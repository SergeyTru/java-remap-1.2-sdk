package com.lognex.api.entities.agents;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class EmployeeEntity extends AgentEntity {
    private EmployeeEntity owner;
    private Boolean shared;
    private String lastName;
    private String externalCode;
    private String shortFio;
    private LocalDateTime created;
    private String fullName;
    private Boolean archived;
    private Boolean uid;
    private EmployeeEntity cashier;
    private String updated;
    private String email;
    private GroupEntity group;
}
