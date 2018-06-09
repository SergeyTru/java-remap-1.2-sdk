package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.CurrencyEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CurrencyClient implements GetListEndpoint<CurrencyEntity>, PostEndpoint<CurrencyEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/currency";
    }

    CurrencyClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Валют
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<CurrencyEntity> get() throws IOException, LognexApiException {
        return get(api, CurrencyEntity.class);
    }

    /**
     * Создание новой Валюты
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public void post(CurrencyEntity newEntity) throws IOException, LognexApiException {
        post(api, newEntity, CurrencyEntity.class);
    }
}