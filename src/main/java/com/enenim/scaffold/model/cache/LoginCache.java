package com.enenim.scaffold.model.cache;

import com.enenim.scaffold.constant.ModelFieldConstant;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.dao.Tracker;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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

    private Tracker tracker;

    @JsonProperty("last_logged_in")
    private java.sql.Date lastLoggedIn;

    @JsonProperty("status")
    private EnabledStatus enabled;

    @JsonProperty("global_settings")
    private HashMap<String, Object> globalSettings = new HashMap<>();

    @JsonProperty(ModelFieldConstant.CREATED_AT)
    private java.sql.Date createdAt;

    @JsonProperty(ModelFieldConstant.UPDATED_AT)
    private java.sql.Date updatedAt;

    /**
     * This is use for token expiration time
     */
    private java.util.Date created;

    public boolean hasExpired() {
        if(getCreated() == null){
            return true;
        }
        LocalDateTime localDateTime = getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusHours(1);
        return  Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).before(new Date());
    }
}
