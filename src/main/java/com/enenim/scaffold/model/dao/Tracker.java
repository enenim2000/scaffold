package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.LoggedIn;
import com.enenim.scaffold.model.BaseModel;
import com.enenim.scaffold.util.JsonDateSerializer;
import com.enenim.scaffold.util.RequestUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "trackers")
@ToString
public class Tracker extends BaseModel {
    public Tracker(){}

    public Tracker (Login login, Date date){
        setDateLoggedIn(date);
        setIpAddress(RequestUtil.getIpAddress());
        setUserAgent(RequestUtil.getUserAgent());
        setLoggedIn(LoggedIn.USER_LOGGED_IN);
        setSessionId(new PasswordEncoder().encode(date.toString()) + Math.random());
        setFailedAttempts(0);
        setTimeOfLastActivity(date);
        setLogin(login);
    }

    @Column(length = 200, unique = true)
    @JsonProperty("session_id")
    @SerializedName("session_id")
    private String sessionId;

    @Column(length = 15)
    @JsonProperty("ip_address")
    @SerializedName("ip_address")
    private String ipAddress;

    @JsonProperty("user_agent")
    @SerializedName("user_agent")
    private String userAgent;

    @JsonProperty("date_logged_in")
    @SerializedName("date_logged_in")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dateLoggedIn;

    @JsonProperty("date_logged_out")
    @SerializedName("date_logged_out")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dateLoggedOut;

    @JsonProperty("time_of_last_activity")
    @SerializedName("time_of_last_activity")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date timeOfLastActivity;

    @NotNull
    @Column(length = 4)
    @JsonProperty("failed_attempts")
    private int failedAttempts;

    @NotNull
    @JsonProperty("logged_in")
    private LoggedIn loggedIn = LoggedIn.USER_NOT_LOGGED_IN;

    @NonNull
    @ManyToOne
    @JsonManagedReference
    private Login login;
}
