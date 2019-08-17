package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.util.ObjectMapperUtil;
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

    @NotBlank(message = "@{consumer.first_name.required}")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "@{consumer.last_name.required}")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "@{consumer.email.required}")
    @Email(message = "@{consumer.email.pattern}")
    private String email;

    @NotBlank(message = "@{password.required}")
    private String password;

    @NotBlank(message = "@{confirm_password.required}")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @Override
    public Consumer buildModel() {
        return ObjectMapperUtil.map(this, Consumer.class);
    }

    @Override
    public Consumer buildModel(Consumer consumer) {
        return ObjectMapperUtil.map(this, consumer);
    }
}