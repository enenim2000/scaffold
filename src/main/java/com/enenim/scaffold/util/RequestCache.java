package com.enenim.scaffold.util;

import com.enenim.scaffold.enums.AuditStatus;
import com.enenim.scaffold.model.cache.LoginCache;
import com.enenim.scaffold.model.dao.*;
import com.enenim.scaffold.util.setting.ConsumerSystemSetting;
import com.enenim.scaffold.util.setting.VendorSystemSetting;
import lombok.Data;

import java.util.HashMap;

@Data
public class RequestCache {
    private String rid;
    private String lang;
    private Login login;
    private Staff staff;
    private Vendor vendor;
    private String message;
    private String fullname;
    private String ipAddress;
    private String userAgent;
    private String taskRoute;
    private String userAction;
    private Consumer consumer;
    private String requestBody;
    private LoginCache loginToken;
    private PaymentChannel channel;
    private AuditStatus auditStatus;
    private Authorization authorization;
    private HashMap<String, VendorSystemSetting> vendorSystemSettings;
    private HashMap<String, ConsumerSystemSetting> consumerSystemSettings;
}
