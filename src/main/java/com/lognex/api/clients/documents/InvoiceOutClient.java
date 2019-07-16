package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.InvoiceOut;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class InvoiceOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<InvoiceOut>,
        PostEndpoint<InvoiceOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<InvoiceOut>,
        PutByIdEndpoint<InvoiceOut>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public InvoiceOutClient(ApiClient api) {
        super(api, "/entity/invoiceout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InvoiceOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}