package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Consumer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ConsumerRequest extends RequestBody<Consumer>{

    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    public Consumer validateRequest() {
        Consumer consumer = new Consumer();
        consumer.setFirstName(firstName);
        consumer.setLastName(lastName);
        consumer.setEmail(email);
        consumer.setPhone(phone);
        return consumer;
    }

    public Consumer validateRequest(Consumer consumer) {
        consumer.setFirstName(firstName);
        consumer.setLastName(lastName);
        consumer.setEmail(email);
        consumer.setPhone(phone);
        return consumer;
    }
}