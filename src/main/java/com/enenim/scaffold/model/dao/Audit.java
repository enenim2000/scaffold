package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "audits")
public class Audit extends BaseModel {

    @Column(length = 100)
    @JsonProperty("entity_type")
    private String entityType;

    @Column(length = 80)
    @JsonProperty("task_route")
    private String taskRoute;

    @Column(length = 200)
    @JsonProperty("user_action")
    private String userAction;

    @Column(length = 20)
    @JsonProperty("crud_action")
    private String crudAction;

    @Column(length = 20)
    private String ip;

    @Column(length = 150)
    @JsonProperty("user_agent")
    private String userAgent;

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String dependency;

    private AuditStatus status = AuditStatus.ACTIVE;

    @Column(name = "_before", columnDefinition = DataTypeConstant.TEXT)
    private String before;

    @Column(name = "_after", columnDefinition = DataTypeConstant.TEXT)
    private String after;

    @OneToOne
    @ApiModelProperty(required = true, hidden = true)
    private Login login;

    @ManyToOne
    @ApiModelProperty(required = true, hidden = true)
    private Authorization authorization;
}