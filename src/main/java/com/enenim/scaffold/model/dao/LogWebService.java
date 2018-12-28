package com.enenim.scaffold.model.dao;

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
@Table(name = "log_web_services")
public class LogWebService extends BaseModel {

    @NotNull
    @Column(length = 80)
    private String login;

    @NotNull
    @Column(length = 50)
    private String type;

    @NotNull
    @Column(length = 200)
    private String narration;

    @NotNull
    private String endpoint;

    @NotNull
    @Column(length = 10)
    private String httpMethod;

    @NotNull
    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String request;

    @NotNull
    @Column(length = 10)
    @JsonProperty("status_code")
    private String statusCode;

    @NotNull
    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String response;
}
