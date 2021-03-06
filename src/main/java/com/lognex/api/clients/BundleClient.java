package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.Bundle;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeResponse;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public final class BundleClient
        extends EntityClientBase
        implements
        GetListEndpoint<Bundle>,
        PostEndpoint<Bundle>,
        MetadataEndpoint<MetadataAttributeResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Bundle>,
        PutByIdEndpoint<Bundle>,
        MassCreateUpdateDeleteEndpoint<Bundle>,
        DeleteByIdEndpoint,
        HasImagesEndpoint<Bundle> {

    public BundleClient(ApiClient api) {
        super(api, "/entity/bundle/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Bundle.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeResponse.class;
    }

    @ApiEndpoint
    public ListEntity<Bundle.ComponentEntity> getComponents(String bundleId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + bundleId + "/components").
                apiParams(params).
                list(Bundle.ComponentEntity.class);
    }

    @ApiEndpoint
    public ListEntity<Bundle.ComponentEntity> getComponents(Bundle bundle, ApiParam... params) throws IOException, ApiClientException {
        return getComponents(bundle.getId(), params);
    }

    @ApiEndpoint
    public Bundle.ComponentEntity getComponent(String bundleId, String componentId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + bundleId + "/components/" + componentId).
                apiParams(params).
                get(Bundle.ComponentEntity.class);
    }

    @ApiEndpoint
    public Bundle.ComponentEntity getComponent(Bundle bundle, String componentId, ApiParam... params) throws IOException, ApiClientException {
        return getComponent(bundle.getId(), componentId, params);
    }

    @ApiEndpoint
    public Bundle.ComponentEntity getComponent(Bundle bundle, Bundle.ComponentEntity component, ApiParam... params) throws IOException, ApiClientException {
        return getComponent(bundle, component.getId(), params);
    }

    @ApiEndpoint
    public void deleteComponent(String bundleId, String componentId) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + bundleId + "/components/" + componentId).
                delete();
    }

    @ApiEndpoint
    public void deleteComponent(Bundle entity, String componentId) throws IOException, ApiClientException {
        deleteComponent(entity.getId(), componentId);
    }

    @ApiEndpoint
    public void deleteComponent(Bundle entity, Bundle.ComponentEntity component) throws IOException, ApiClientException {
        deleteComponent(entity, component.getId());
    }
}
