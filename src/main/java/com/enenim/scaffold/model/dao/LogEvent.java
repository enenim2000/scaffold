package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.LogEventFlaggedType;
import com.enenim.scaffold.enums.LogEventStatus;
import com.enenim.scaffold.enums.LogEventType;
import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "log_events")
public class LogEvent extends BaseModel {
    @NotNull
    @Column(length = 80)
    @JsonProperty("task_route")
    private String taskRoute = "";

    @NotNull
    @Column(length = 30)
    @JsonProperty("task_name")
    private String taskName = "";

    @NotNull
    @Column(length = 80)
    private String login = "";

    @NotNull
    @JsonProperty("user_name")
    private String userName = "";

    @NotNull
    @JsonProperty("user_action")
    private String userAction = "";

    @NotNull
    private LogEventType type = LogEventType.ORDINARY;

    @NotNull
    @JsonProperty("flagged_type")
    private LogEventFlaggedType flaggedType = LogEventFlaggedType.NORMAL;

    @NotNull
    private LogEventStatus status;

    @NotNull
    @JsonProperty("request_data")
    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String requestData;

    @NotNull
    @JsonProperty("response_data")
    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String responseData;

    @NotNull
    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String details = "";

    @NotNull
    private String uri;

    @Column(length = 50)
    @JsonProperty("ip_address")
    private String ipAddress;

    @Column(length = 100)
    private String location;
}
