package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.interfaces.DataTypeConstant;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "transaction_extras")
public class TransactionExtra extends BaseModel {

    @NotNull
    @OneToOne
    private Transaction transaction;

    @Column(length = 100)
    @JsonProperty("terminal_id")
    private String terminalId;

    @Column(length = 50)
    @JsonProperty("account_agent_branch")
    private String accountAgentBranch;

    @Column(length = 15)
    @JsonProperty("ip_address")
    private String ipAddress;

    @NotNull
    @Column(length = 30)
    @JsonProperty("subcription_type")
    private String subscriptionType;

    @NotNull
    @Column(length = 10)
    @JsonProperty("subcription_id")
    private Integer subscriptionId;

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String payloads = "";

    @Column(columnDefinition = DataTypeConstant.TEXT)
    private String payer = "";

    @Column(length = 60)
    private String customerEmail;

    @Column(length = 40)
    private String customerPhone;
}
