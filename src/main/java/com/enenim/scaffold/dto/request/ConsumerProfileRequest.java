package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.enums.Gender;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsumerProfileRequest extends RequestBody<Consumer> {

    @JsonProperty("first_name")
    @SerializedName("first_name")
    private String firstName;

    @JsonProperty("last_name")
    @SerializedName("last_name")
    private String lastName;

    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    private String phoneNumber;

    @JsonProperty("date_of_birth")
    @SerializedName("date_of_birth")
    private String dateOfBirth;

    private String bvn;

    private Gender gender;

    @JsonProperty("youtube_url")
    @SerializedName("youtube_url")
    private String youtubeUrl;

    @JsonProperty("facebook_url")
    @SerializedName("facebook_url")
    private String facebookUrl;

    @JsonProperty("twitter_url")
    @SerializedName("twitter_url")
    private String twitterUrl;

    @JsonProperty("logo_url")
    @SerializedName("logo_url")
    private String logoUrl;

    @Override
    Consumer buildModel() {
        return ObjectMapperUtil.map(this, Consumer.class);
    }

    @Override
    Consumer buildModel(Consumer model) {
        return ObjectMapperUtil.map(this, model);
    }
}