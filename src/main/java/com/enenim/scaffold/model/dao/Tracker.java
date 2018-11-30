package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.LoggedIn;
import com.enenim.scaffold.model.BaseModel;
import com.enenim.scaffold.util.RequestUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
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
        setSessionId(new BCryptPasswordEncoder().encode(date.toString()) + Math.random());
        setFailedAttempts(0);
        setLogin(login);
    }

    @NonNull
    @OneToOne
    private Login login;

    @Column(length = 200)
    @JsonProperty("session_id")
    private String sessionId;

    @Column(length = 15)
    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("date_logged_in")
    private Date dateLoggedIn;

    @JsonProperty("date_logged_out")
    private Date dateLoggedOut;

    @JsonProperty("time_of_last_activity")
    private Timestamp timeOfLastActivity;

    @NotNull
    @Column(length = 4)
    @JsonProperty("failed_attempts")
    private int failedAttempts;

    @NotNull
    @JsonProperty("logged_in")
    private LoggedIn loggedIn = LoggedIn.USER_NOT_LOGGED_IN;
}
