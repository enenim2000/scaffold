package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.constant.RoleConstant;

import java.util.HashMap;

public class ConsumerSettingConfigUtil {

    private static final String CONSUMER_ONLY = "[ " + RoleConstant.CONSUMER + "]";
    private static final String STAFF_CONSUMER_ONLY = "[ " + RoleConstant.STAFF + "," + RoleConstant.CONSUMER + "]";
    private static final String STAFF_ONLY = "[ " + RoleConstant.STAFF + "]";

    private static final HashMap<String, String> TRANSACTION_CONFIG = new HashMap<String, String>(){{
        put("transaction_amount", "{\"type\":\"number\",\"label\": \"Transaction Amount Limit Per Day\", \"placeholder\": \"Enter transaction amount limit per day\", \"value\":\"10\", \"maxlength\":\"1\", \"minlength\":\"100\", \"options\":null, \"user_types\":" + STAFF_CONSUMER_ONLY + "}");
    }};

    private static final HashMap<String, String> GENERAL_CONFIG = new HashMap<String, String>(){{
        put("support_notification", "{\"type\":\"text\",\"label\": \"Support Notification\", \"placeholder\": \"Enter notification emails\", \"value\":\"enenim.asukwo@curexzone.com,luther.emmanuel@curexzone.com\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":null, \"user_types\":" + STAFF_CONSUMER_ONLY + "}");
    }};

    private static final HashMap<String, String> NOTIFICATION_CONFIG = new HashMap<String, String>(){{
        put("new_releases", "{\"type\":\"checkbox\",\"label\": \"Notify me for new release\", \"placeholder\": \"Notify me on release news/updates\", \"value\":\"yes\", \"maxlength\":\"\", \"minlength\":\"\", \"options\":[{ \"key\": \"yes\", \"value\": \"Yes\" }, { \"key\": \"no\", \"value\": \"No\" } ], \"user_types\":" + CONSUMER_ONLY + "}");
    }};
}
