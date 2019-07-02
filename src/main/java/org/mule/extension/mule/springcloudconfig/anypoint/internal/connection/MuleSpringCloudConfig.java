package org.mule.extension.mule.springcloudconfig.anypoint.internal.connection;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

import static org.mule.extension.mule.springcloudconfig.anypoint.internal.MuleSpringCloudConfigConstants.SPRING_CONFIG_SERVER;

public class MuleSpringCloudConfig {
    /**
     * The base URL where the Spring Cloud Config API is hosted.
     */
    @Parameter
    @Placement(tab = SPRING_CONFIG_SERVER)
    @Example("http://something.com:8000")
    @Summary("The base URL along with port where the Spring Cloud Config API is hosted.")
    private String configServerBaseUrl;
    /**
     * The name of the application whose properties will be read. If not specified, mule app name will be
     * used.
     */
    @DisplayName("Application Name")
    @Example("eureka")
    @Summary("The name of the application whose properties will be read. If not specified, mule app name will be used.")
    @Parameter
    private String applicationName;
    /**
     * The profiles to take into consideration. This is a comma-separated list. If empty, this module
     * should try to locate spring profiles.
     */
    @DisplayName("Profiles")
    @Parameter
    @Example("dev")
    @Summary("The profiles to take into consideration. This is a comma-separated list. If empty, this module should try to locate spring profiles.")
    private String profiles;
    /**
     * The tag for the configuration. Useful for versioning.
     */
    @DisplayName("Label")
    @Example("docker")
    @Summary("The tag for the configuration. Useful for versioning.")
    @Parameter
    private String label;
    @Parameter
    @Example("/path")
    @Summary("Additional path used, if Spring Config server runs with this url: http://something:8000/Path")
    @Optional(defaultValue = "")
    @DisplayName("Base Path")
    private String basePath;

    @Override
    public String toString() {
        return "MuleSpringCloudConfig{" +
                "configServerBaseUrl='" + getConfigServerBaseUrl() + '\'' +
                ", applicationName='" + getApplicationName() + '\'' +
                ", profiles='" + getProfiles() + '\'' +
                ", label='" + getLabel() + '\'' +
                ", basePath='" + getBasePath() + '\'' +
                '}';
    }

    public String getConfigServerBaseUrl() {
        return configServerBaseUrl;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getProfiles() {
        return profiles;
    }

    public String getLabel() {
        return label;
    }

    public String getBasePath() {
        return basePath;
    }

}
