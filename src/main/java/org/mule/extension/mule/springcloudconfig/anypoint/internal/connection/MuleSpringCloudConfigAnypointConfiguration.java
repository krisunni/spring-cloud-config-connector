package org.mule.extension.mule.springcloudconfig.anypoint.internal.connection;

import org.mule.extension.mule.springcloudconfig.anypoint.internal.operations.MuleSpringCloudConfigAnypointOperations;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(MuleSpringCloudConfigAnypointOperations.class)
@ConnectionProviders(MuleSpringCloudConfigAnypointConnectionProvider.class)
public class MuleSpringCloudConfigAnypointConfiguration {

}
