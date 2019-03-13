package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.Gender;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ConsumerProfileResponse extends BaseModel {

    @JsonProperty("first_name")
    @SerializedName("first_name")
    private String firstName;

    @JsonProperty("last_name")
    @SerializedName("last_name")
    private String lastName;

    @NotNull
    @JsonProperty("email")
    @SerializedName("email")
    private String email;

    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    private String phoneNumber;

    @JsonProperty("date_of_birth")
    @SerializedName("date_of_birth")
    private String dateOfBirth;

    private String bvn;

    private Gender gender;

    @JsonProperty("youtube_url")
    private String youtubeUrl;

    @JsonProperty("facebook_url")
    private String facebookUrl;

    @JsonProperty("twitter_url")
    private String twitterUrl;

    @JsonProperty("logo_url")
    private String logoUrl;

    private VerifyStatus verified;

    private EnabledStatus enabled;
}