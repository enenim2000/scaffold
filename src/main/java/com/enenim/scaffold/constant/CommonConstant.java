package com.enenim.scaffold.constant;

import java.io.File;

public interface CommonConstant {
    int PAGE_SIZE = 50;

    String PAGE = "page";
    String Q = "q";
    String FROM = "from";
    String TO = "to";
    String IP_ADDRESS = "ip_address";
    String USER_AGENT = "user_agent";

    String MESSAGE = "message";
    String PER_PAGE = "per_page";

    String DIR_HOME = "user.home";
    String ASSET_BASE = System.getProperty(DIR_HOME) + File.separator + CommonConstant.class + File.separator;
    String API_KEY = "Api-Key";
    String PLACE_HOLDER = "{}";

    String LOGIN_ID = "login_id";
    String LOGIN = "login";

    Integer SETTING_TIMEOUT = 24;
}