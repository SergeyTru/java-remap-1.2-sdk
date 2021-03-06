package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.discounts.BonusProgram;

public final class BonusProgramClient
        extends EntityClientBase
        implements
        GetListEndpoint<BonusProgram>,
        GetByIdEndpoint<BonusProgram>,
        PostEndpoint<BonusProgram>,
        PutByIdEndpoint<BonusProgram>,
        DeleteByIdEndpoint {

    public BonusProgramClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/bonusprogram/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return BonusProgram.class;
    }
}
