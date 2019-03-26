package com.enenim.scaffold.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class TicketHistory {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("user_type")
    private String userType;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("service_id")
    private Long serviceId;

    @JsonProperty("service_name")
    private String serviceName;

    private String comment;

    private String date;

    public static List<TicketHistory> getTicketHistories(String json){
        try {
            return new ObjectMapper().readValue(json, new TypeReference<List<TicketHistory>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}