package com.enenim.scaffold.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServiceRequest {

    @JsonProperty("service_id")
    private Long serviceId;

    /**
     * This field stores the edited payload form presented to the user/consumer
     */
    @JsonProperty("consumer_form")
    private String consumerForm;

}
