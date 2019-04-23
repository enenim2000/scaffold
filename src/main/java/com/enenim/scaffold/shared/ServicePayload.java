package com.enenim.scaffold.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ServicePayload {

    @JsonProperty("form_pages")
    Map<String, ServiceFormPage> formPages;

}
