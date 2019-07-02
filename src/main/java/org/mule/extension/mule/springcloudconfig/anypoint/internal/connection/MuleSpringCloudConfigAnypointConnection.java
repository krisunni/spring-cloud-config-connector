package org.mule.extension.mule.springcloudconfig.anypoint.internal.connection;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import static org.mule.extension.mule.springcloudconfig.anypoint.internal.MuleSpringCloudConfigConstants.*;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class MuleSpringCloudConfigAnypointConnection {

    public final Logger Logger = LoggerFactory.getLogger(MuleSpringCloudConfigAnypointConnection.class);
    private final MuleSpringCloudConfig muleSpringCloudConfig;
    private final int connectionTimeout;
    private HttpClient httpClient;
    private HttpRequestBuilder httpRequestBuilder;
    private Properties props;
    private String url;

    public MuleSpringCloudConfigAnypointConnection(HttpService httpService, MuleSpringCloudConfig muleSpringCloudConfig, int connectionTimeout) throws ConnectionException {
        this.muleSpringCloudConfig = muleSpringCloudConfig;
        this.connectionTimeout = connectionTimeout;
        Logger.debug("Using Config:" + muleSpringCloudConfig.toString());
        if (muleSpringCloudConfig.getBasePath() == null
                || muleSpringCloudConfig.getLabel() == null
                || muleSpringCloudConfig.getProfiles() == null
                || muleSpringCloudConfig.getApplicationName() == null
                || muleSpringCloudConfig.getConfigServerBaseUrl() == null) {
            throw new ConnectionException("Error Building the URL" + muleSpringCloudConfig.toString());
        } else if (muleSpringCloudConfig.getBasePath().isEmpty()) {
            url = muleSpringCloudConfig.getConfigServerBaseUrl() + "/"
                    + muleSpringCloudConfig.getBasePath() + "/"
                    + muleSpringCloudConfig.getApplicationName() + "/"
                    + muleSpringCloudConfig.getProfiles();
        } else {
            url = muleSpringCloudConfig.getConfigServerBaseUrl() + "/"
                    + muleSpringCloudConfig.getApplicationName() + "/"
                    + muleSpringCloudConfig.getProfiles();
        }
        Logger.debug("Using Connecting URL: " + url);
        initHttpClient(httpService);
        try {
            getProperties();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public MuleSpringCloudConfig getMuleSpringCloudConfig() {
        return muleSpringCloudConfig;
    }

    /**
     * @param httpService
     */
    public void initHttpClient(HttpService httpService) {
        HttpClientConfiguration.Builder builder = new HttpClientConfiguration.Builder();
        builder.setName("SpringCloudConfig");
        httpClient = httpService.getClientFactory().create(builder.build());
        httpRequestBuilder = HttpRequest.builder();
        httpClient.start();
    }

    public boolean isConnected() throws Exception {
        HttpRequest request = httpRequestBuilder
                .method(HttpConstants.Method.GET)
                .uri(url)
                .build();
        Logger.debug("isConnected with request: " + request.toString());
        HttpResponse httpResponse = httpClient.send(request, connectionTimeout, false, null);
        if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300)
            return true;
        else
            throw new ConnectionException("Error connecting to the server: Error Code " + httpResponse.getStatusCode()
                    + "~" + httpResponse + "Using URL:" + url);
    }

    public void invalidate() {
        httpClient.stop();
    }

    public void getProperties() throws IOException, TimeoutException {
        props = new Properties();
        Logger.debug("Setting up connector with properties: " + muleSpringCloudConfig.toString());
        MultiMap<String, String> headerMap = new MultiMap<>();
        headerMap.put(HttpHeaders.ACCEPT, "application/json");
        HttpRequest request = httpRequestBuilder
                .method(HttpConstants.Method.GET)
                .uri(url)
                .headers(headerMap)
                .build();
        HttpResponse httpResponse = httpClient.send(request, connectionTimeout, false, null);
        if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300) {
            InputStream content = httpResponse.getEntity().getContent();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> springConfigMap = mapper.readValue(content, Map.class);
            Logger.debug("Got settings from cloud config server: " + springConfigMap.toString());
            List<Map> sources = (List<Map>) springConfigMap.get(PROPERTY_SOURCES_PROPERTY);
            Logger.debug("Property sources are: " + sources);
            if (sources != null) {
                for (int sourcesIterator = sources.size() - 1; sourcesIterator >= 0; sourcesIterator--) {
                    Map<String, String> source = (Map<String, String>) sources.get(sourcesIterator).get(SOURCE_PROPERTY);
                    String name = (String) sources.get(sourcesIterator).get(NAME_PROPERTY);
                    if (name != null && Logger.isDebugEnabled()) {
                        Logger.debug("Reading properties from source: " + name);
                    }
                    for (Map.Entry<String, String> entry : source.entrySet()) {
                        if (Logger.isDebugEnabled()) {
                            Logger.debug("Read property with key: " + entry.getKey() + " from source.");
                        }
                        props.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }

    public String resolvePlaceholder(String placeholder) {
        Logger.debug("Call to resolve placeholder: " + placeholder);
        String value = this.props.getProperty(placeholder, null);
        if (value != null) {
            Logger.debug("Found key in config server");
            return value;
        }

        Logger.debug("Key not found in config server, returning all props");
        if (Logger.isDebugEnabled()) {
            return props.toString();
        } else
            return "";
    }
}
