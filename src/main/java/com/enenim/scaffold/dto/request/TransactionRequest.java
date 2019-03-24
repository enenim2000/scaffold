package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.dto.request.part.ServiceRequest;
import com.enenim.scaffold.model.dao.Transaction;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class TransactionRequest extends RequestBody<Transaction> {

    /**
     * This field will be use for populating ServiceForm table
     */
    private List<ServiceRequest> services;

    private Date date;

    @JsonProperty("start_time")
    private LocalTime startTime;

    /**
     * Duration in minutes
     */
    private Long duration;

    private String location;

    @JsonProperty("consumer_id")
    private Long consumerId;

    @Override
    Transaction buildModel() {
        return ObjectMapperUtil.map(this, Transaction.class);
    }

    @Override
    Transaction buildModel(Transaction model) {
        return ObjectMapperUtil.map(this, model);
    }
}
