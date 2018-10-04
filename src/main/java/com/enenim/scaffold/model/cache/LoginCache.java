package com.enenim.scaffold.model.cache;

import com.enenim.scaffold.model.dao.Tracker;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
public class LoginCache {
    private Long id;
    private Tracker tracker;
    private Date created = new Date();

    public boolean hasExpired() {
        if(getCreated() == null){
            return true;
        }
        LocalDateTime localDateTime = getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusHours(1);
        return  Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).before(new Date());
    }
}
