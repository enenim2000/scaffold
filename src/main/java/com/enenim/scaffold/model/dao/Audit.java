package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "audits")
public class Audit extends BaseModel {

    @Column(length = 100)
    @JsonProperty("entity_type")
    private String entityType; //When new entity are created on the fly how to ensure they used the same authorization id

    @Column(length = 80)
    @JsonProperty("task_route")
    private String taskRoute;

    @Column(length = 200)
    @JsonProperty("user_action")
    private String userAction; //Create, Update or Delete

    @Column(length = 20)
    @JsonProperty("crud_action")
    private String crudAction; // Created new Staff, Created new Group, Updated existing group

    @Column(length = 20)
    private String ip;

    @Column(length = 150)
    @JsonProperty("user_agent")
    private String userAgent;

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String dependency;

    @NotNull
    private AuditStatus status = AuditStatus.ACTIVE;

    @Column(name = "_before", columnDefinition = DataTypeConstant.TEXT)
    private String before;

    @Column(name = "_after", columnDefinition = DataTypeConstant.TEXT)
    private String after;

    @OneToOne
    private Login login;

    @ManyToOne
    private Authorization authorization;

    public void setBefore(String before){
        this.before = before;
    }

    public void setAfter(String after){
        this.after = after;
    }
}