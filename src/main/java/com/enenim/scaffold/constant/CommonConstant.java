package com.enenim.scaffold.constant;

import java.io.File;

public interface CommonConstant {
    int PAGE_SIZE = 50;

    String PAGE = "getPage";
    String Q = "getQ";
    String FROM = "getFrom";
    String TO = "getTo";
    String IP_ADDRESS = "ip_address";
    String USER_AGENT = "user_agent";

    String MESSAGE = "message";
    String PER_PAGE = "per_page";

    String DIR_HOME = "app.home";
    String ASSET_BASE = System.getProperty(DIR_HOME) + File.separator + "scaffold" + File.separator;
    String API_KEY = "api-key";
    String PLACE_HOLDER = "{}";

    String LOGIN_ID = "login_id";
    String LOGIN = "login";

    Integer IDLE_TIMEOUT = 5; //In minutes
    String LANG = "lang";
    String COMMENT = "comment";
    String STATUS = "status";
    String AUTHORIZATION = "authorization";
    String AUDIT_STATUS = "audit_status";

    String PAYMENT_CHANNEL = "payment_channel";
    String NOT_FOUND = "not_found";
    String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
}