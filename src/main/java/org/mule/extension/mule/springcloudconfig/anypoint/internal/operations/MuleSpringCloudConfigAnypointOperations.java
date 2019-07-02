package org.mule.extension.mule.springcloudconfig.anypoint.internal.operations;

import org.mule.extension.mule.springcloudconfig.anypoint.internal.connection.MuleSpringCloudConfigAnypointConnection;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class MuleSpringCloudConfigAnypointOperations {

    @MediaType(value = ANY, strict = false)
    @DisplayName("Get Properties")
    //@OutputResolver(output = MuleSpringCloudConfigAnypointOutputResolver.class)
    public String getProperties(@Connection MuleSpringCloudConfigAnypointConnection connection, String placeholder) {
        return connection.resolvePlaceholder(placeholder);
    }
}
