package com.enenim.scaffold.util;

import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.*;
import lombok.Data;

import java.util.HashMap;

@Data
public class RequestCache {
    private String rid;
    private String lang;
    private Login login;
    private Staff staff;
    private Vendor vendor;
    private PaymentChannel channel;
    private String message;
    private String fullname;
    private String taskRoute;
    private Consumer consumer;
    private String ipAddress;
    private String userAgent;
    private String userAction;
    private String requestBody;
    private LoginCache loginToken;
    private AuditStatus auditStatus;
    private Authorization authorization;
    private HashMap<String, ConsumerSetting> consumerSettings;
}
