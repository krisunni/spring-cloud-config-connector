package org.mule.extension.mule.springcloudconfig.anypoint.internal;

import org.mule.extension.mule.springcloudconfig.anypoint.internal.connection.MuleSpringCloudConfigAnypointConfiguration;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


@Xml(prefix = "mule-spring-cloud-config")
@Extension(name = "Spring Cloud Config")
@Configurations(MuleSpringCloudConfigAnypointConfiguration.class)
public class MuleSpringCloudConfigAnypointExtension {

}
