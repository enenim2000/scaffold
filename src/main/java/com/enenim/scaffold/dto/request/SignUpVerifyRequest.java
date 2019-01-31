package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpVerifyRequest extends RequestBody<Consumer>{

    //@NotBlank(message = "@{consumer.password.required}")
    private String password;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("phone_number")
    //@NotBlank(message = "@{consumer.phone_number.required}")
    private String phoneNumber;

    @Override
    public Consumer buildModel() {
        return ObjectMapperUtil.map(this, Consumer.class);
    }

    @Override
    public Consumer buildModel(Consumer consumer) {
        return ObjectMapperUtil.map(this, consumer);
    }
}
