package org.mule.extension.mule.springcloudconfig.anypoint;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mule.extension.mule.springcloudconfig.anypoint.internal.connection.MuleSpringCloudConfig;
import org.mule.extension.mule.springcloudconfig.anypoint.internal.connection.MuleSpringCloudConfigAnypointConnection;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientFactory;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MuleSpringCloudConfigAnypointOperationsTestCase {

    public final org.slf4j.Logger Logger = LoggerFactory.getLogger(MuleSpringCloudConfigAnypointConnection.class);
    private final int connectionTimeout = 5000;
    private HttpClient httpClient = mock(HttpClient.class, Mockito.RETURNS_DEEP_STUBS);
    private InputStream inputStream;
    private HttpService httpService = mock(HttpService.class);
    private MuleSpringCloudConfig muleSpringCloudConfig = mock(MuleSpringCloudConfig.class);
    private HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private HttpResponse httpResponse = mock(HttpResponse.class, Mockito.RETURNS_DEEP_STUBS);
    private MuleSpringCloudConfigAnypointConnection muleSpringCloudConfigAnypointConnection;

    @Before
    public void init() throws IOException {
        MockitoAnnotations.initMocks(this);
        inputStream = IOUtils.toInputStream("{}", "UTF-8");
    }

    @After
    public void clear() throws IOException {
    }

    private void setMuleSpringCloudConfig() {
        when(muleSpringCloudConfig.getBasePath()).thenReturn("basePath");
        when(muleSpringCloudConfig.getApplicationName()).thenReturn("MuleSpringCloudConfigAnypointConnectionApplicationName");
        when(muleSpringCloudConfig.getConfigServerBaseUrl()).thenReturn("http://ConfigServerBaseUrl");
        when(muleSpringCloudConfig.getProfiles()).thenReturn("Profiles");
        when(muleSpringCloudConfig.getLabel()).thenReturn("Label");
    }

    @Test
    public void muleConfigTestWithNullProperties() {
        when(muleSpringCloudConfig.getBasePath()).thenReturn(null);
        when(muleSpringCloudConfig.getApplicationName()).thenReturn("MuleSpringCloudConfigAnypointConnectionApplicationName");
        when(muleSpringCloudConfig.getConfigServerBaseUrl()).thenReturn("http://ConfigServerBaseUrl");
        when(muleSpringCloudConfig.getProfiles()).thenReturn("Profiles");
        when(muleSpringCloudConfig.getLabel()).thenReturn("Label");
    }

    @Test
    public void executeGetPropertiesWithEmptyProps() throws Exception {
        when(httpResponse.getStatusCode()).thenReturn(200);
        when(httpService.getClientFactory()).thenReturn(httpClientFactory);
        when(httpClientFactory.create(any())).thenReturn(httpClient);
        setMuleSpringCloudConfig();
        when(httpClientFactory.create(any())).thenReturn(httpClient);
        when(httpClient.send(any(), anyInt(), anyBoolean(), any())).thenReturn(httpResponse);
        when(httpResponse.getEntity().getContent()).thenReturn(inputStream);
        muleSpringCloudConfigAnypointConnection = new MuleSpringCloudConfigAnypointConnection(httpService, muleSpringCloudConfig, connectionTimeout);
        String resolvePlaceholder = muleSpringCloudConfigAnypointConnection.resolvePlaceholder("something");
        assertThat(resolvePlaceholder, is("{}"));
    }

    @Test
    public void executeGetPropertiesWithInValidProps() throws Exception {
        when(httpResponse.getStatusCode()).thenReturn(200);
        when(httpService.getClientFactory()).thenReturn(httpClientFactory);
        when(httpClientFactory.create(any())).thenReturn(httpClient);
        setMuleSpringCloudConfig();
        when(httpClientFactory.create(any())).thenReturn(httpClient);
        when(httpClient.send(any(), anyInt(), anyBoolean(), any())).thenReturn(httpResponse);
        inputStream = new FileInputStream("src/test/resources/test.json");
        when(httpResponse.getEntity().getContent()).thenReturn(inputStream);
        muleSpringCloudConfigAnypointConnection = new MuleSpringCloudConfigAnypointConnection(httpService, muleSpringCloudConfig, connectionTimeout);
        String resolvePlaceholder = muleSpringCloudConfigAnypointConnection.resolvePlaceholder("something");
        assertTrue(!resolvePlaceholder.isEmpty());
    }


    @Test
    public void executeGetPropertiesWithDummyProps() throws Exception {
        when(httpResponse.getStatusCode()).thenReturn(200);
        when(httpService.getClientFactory()).thenReturn(httpClientFactory);
        when(httpClientFactory.create(any())).thenReturn(httpClient);
        setMuleSpringCloudConfig();
        when(httpClientFactory.create(any())).thenReturn(httpClient);
        when(httpClient.send(any(), anyInt(), anyBoolean(), any())).thenReturn(httpResponse);
        inputStream = new FileInputStream("src/test/resources/test.json");
        when(httpResponse.getEntity().getContent()).thenReturn(inputStream);
        muleSpringCloudConfigAnypointConnection = new MuleSpringCloudConfigAnypointConnection(httpService, muleSpringCloudConfig, connectionTimeout);
        verify(httpResponse.getEntity(), times(1)).getContent();
    }

    @Test
    public void executeResolvePlaceHolder() throws IOException, TimeoutException, ConnectionException {
        when(httpResponse.getStatusCode()).thenReturn(200);
        when(httpService.getClientFactory()).thenReturn(httpClientFactory);
        when(httpClientFactory.create(any())).thenReturn(httpClient);
        setMuleSpringCloudConfig();
        when(httpClientFactory.create(any())).thenReturn(httpClient);
        when(httpClient.send(any(), anyInt(), anyBoolean(), any())).thenReturn(httpResponse);
        inputStream = new FileInputStream("src/test/resources/test.json");
        when(httpResponse.getEntity().getContent()).thenReturn(inputStream);
        muleSpringCloudConfigAnypointConnection = new MuleSpringCloudConfigAnypointConnection(httpService, muleSpringCloudConfig, connectionTimeout);
        String resolvePlaceholder = muleSpringCloudConfigAnypointConnection.resolvePlaceholder("info.description");
        assertThat(resolvePlaceholder, is("Spring Cloud Samples"));
    }

}
