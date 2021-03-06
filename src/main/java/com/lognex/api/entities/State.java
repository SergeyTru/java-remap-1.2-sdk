package com.lognex.api.entities;

import com.google.gson.annotations.SerializedName;
import com.lognex.api.ApiClient;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

/**
 * Состояние
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class State extends MetaEntity implements Fetchable {
    /**
     * Цвет Статуса
     */
    private Integer color;

    /**
     * Тип Статуса
     */
    private StateType stateType;

    /**
     * Тип сущности, к которой относится Статус
     */
    private Meta.Type entityType;

    public State(String id) {
        super(id);
    }

    @Override
    public void fetch(ApiClient api) throws IOException, ApiClientException {
        this.set(
                HttpRequestExecutor.url(api, meta.getHref()).get(State.class)
        );
    }

    public enum StateType {
        @SerializedName("Regular") regular,
        @SerializedName("Successful") successful,
        @SerializedName("Unsuccessful") unsuccessful
    }
}
