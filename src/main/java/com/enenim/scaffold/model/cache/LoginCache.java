package com.enenim.scaffold.model.cache;

import com.enenim.scaffold.constant.ModelFieldConstant;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.LoggedIn;
import com.enenim.scaffold.enums.LoginStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Data
public class LoginCache {
    private Long id;

    private String username;

    @JsonProperty("user_type")
    private String userType;

    @JsonProperty("user")
    private Object user;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("login_status")
    private LoginStatus loginStatus;

    @Column(length = 200, unique = true)
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
    private LoggedIn loggedIn = LoggedIn.USER_LOGGED_IN;

    @JsonProperty("last_logged_in")
    private Date lastLoggedIn;

    @JsonProperty("status")
    private EnabledStatus enabled;

    @JsonProperty("global_settings")
    private HashMap<String, Object> globalSettings = new HashMap<>();

    @JsonProperty(ModelFieldConstant.CREATED_AT)
    private Date createdAt;

    @JsonProperty(ModelFieldConstant.UPDATED_AT)
    private Date updatedAt;

    /**
     * This is use for token expiration time
     */
    private Date created;

    public boolean hasExpired(long idleTimeout) {
        if(getCreated() == null){
            return true;
        }
        LocalDateTime localDateTime = getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusMinutes(idleTimeout);
        return  Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).before(new Date());
    }
}
