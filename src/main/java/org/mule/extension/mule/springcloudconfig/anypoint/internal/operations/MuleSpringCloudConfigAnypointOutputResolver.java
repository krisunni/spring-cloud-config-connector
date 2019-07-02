package org.mule.extension.mule.springcloudconfig.anypoint.internal.operations;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.AttributesTypeResolver;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;

import java.util.Properties;

public class MuleSpringCloudConfigAnypointOutputResolver implements OutputTypeResolver<Properties>, AttributesTypeResolver<Properties> {
    @Override
    public MetadataType getAttributesType(MetadataContext metadataContext, Properties properties) throws MetadataResolvingException, ConnectionException {
        return metadataContext.getTypeLoader().load(Properties.class);
    }

    @Override
    public String getResolverName() {
        return "MuleSpringCloudConfigAnypointOutputResolver";
    }

    @Override
    public MetadataType getOutputType(MetadataContext metadataContext, Properties properties) throws MetadataResolvingException, ConnectionException {
        return metadataContext.getTypeLoader().load(Properties.class);

    }

    @Override
    public String getCategoryName() {
        return "Records";
    }
}
