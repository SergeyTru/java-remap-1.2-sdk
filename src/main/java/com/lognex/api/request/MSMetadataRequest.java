package com.lognex.api.request;

import com.lognex.api.ApiClient;
import org.apache.http.client.methods.HttpUriRequest;

public class MSMetadataRequest extends MSRequest {

    public MSMetadataRequest(String url, ApiClient client) {
        super(url, client);
    }

    @Override
    protected HttpUriRequest buildRequest() {
        return null;
    }
}
