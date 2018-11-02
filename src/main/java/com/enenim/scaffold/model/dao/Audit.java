package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.constant.CRUDConstant;
import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.enenim.scaffold.util.RequestUtil;
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
    
    public Audit(){
        setBefore("");
        setAfter("");
        setAction(CRUDConstant.CREATE);
        setAuthorization(null);
        setRid(RequestUtil.getRID());
        setLogin(login);
        setIp(RequestUtil.getIpAddress());
        setUserName(login.getUsername());
        setStatus(AuditStatus.ACTIVE);
        setTaskRoute(RequestUtil.getTaskRoute());
        setTrailId(login.getUserId());
        setTrailType(login.getUserType());
        setTableName("audits");
    }
    
    @OneToOne
    private Login login;

    @JsonProperty("trail_type")
    private String trailType;

    @NotNull
    @JsonProperty("trail_id")
    private Long trailId;

    @ManyToOne
    private Authorization authorization;

    @Column(length = 60)
    @JsonProperty("user_name")
    private String userName = "";

    @Column(length = 80)
    @JsonProperty("task_route")
    private String taskRoute;

    @Column(length = 200)
    @JsonProperty("user_action")
    private String userAction;

    @Column(length = 100)
    @JsonProperty("table_name")
    private String tableName;

    @Column(length = 20)
    private String action;

    @Column(length = 20)
    private String ip;

    @Column(length = 40)
    private String rid;

    @NotNull
    private AuditStatus status = AuditStatus.ACTIVE;

    @Column(name = "_before", columnDefinition = DataTypeConstant.TEXT)
    private String before;

    @Column(name = "_after", columnDefinition = DataTypeConstant.TEXT)
    private String after;

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String dependency;

    public void setBefore(String before){
        this.before = before;
    }

    public void setAfter(String after){
        this.after = after;
    }
}