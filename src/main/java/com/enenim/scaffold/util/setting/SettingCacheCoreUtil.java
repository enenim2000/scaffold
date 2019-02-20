package com.enenim.scaffold.util.setting;

import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.shared.IdName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingCacheCoreUtil {

    //@Autowired
    //private static ActiveHourService activeHourService;

    private static Map<String, String> PAYLOAD_YES_NO = new HashMap<String, String>() {{put("yes", "YES"); put("no", "NO");}};
    private static Map<String, String> PAYLOAD_YES_ONE = new HashMap<String, String>() {{put("1", "Yes"); put("0", "No");}};
    private static Map<String, String> PAYLOAD_NEW_CSR_THRESH = new HashMap<String, String>() {{put("1", "1"); put("2", "2"); put("3", "3"); put("4", "4"); put("5", "5"); put("10", "10"); put("15", "15"); put("20", "20");}};
    private static Map<String, String> PAYLOAD_ENABLED_ONE = new HashMap<String, String>() {{put("1", "Enabled"); put("0", "Disabled");}};
    private static Map<String, String> PAYLOAD_STAGE_ROP_NOTIF = new HashMap<String, String>() {{
        put("1", "Stage 1 (Notify Consumers just once before payment)");
        put("2", "Stage 2 (Notify Consumer twice before payment)");
        put("3", "Stage 3 (Include all three notification stages before payment)");
    }};
    private static Map<String, String> PAYLOAD_MAX_ROP_RETRY = new HashMap<String, String>() {{put("1", "1"); put("2", "2"); put("3", "3"); put("5", "5");}};
    private static Map<String, String> PAYLOAD_ROP_FAIL_DELAY = new HashMap<String, String>() {{put("1", "1 Mins"); put("2", "2 Mins"); put("5", "5 Mins");}};
    private static Map<String, String> PAYLOAD_CONSUMER_SYSTEM_STATE = new HashMap<String, String>() {{put("1", "StartUp"); put("0", "Shutdown");}};
    private static Map<String, String> PAYLOAD_CONSUMER_TRANS = new HashMap<String, String>() {{put("1", "Active"); put("0", "Suspend");}};
    private static Map<String, String> PAYLOAD_FAILURE_NOTIFICATION_FREQ = new HashMap<String, String>() {{put("5", "5 Mins");put("10", "10 Mins"); put("15", "15 Mins"); put("20", "20 Mins"); put("30", "30 Mins"); put("60", "1 Hr");   put("90", "1 Hr 30mins"); put("120", "2 Hrs"); put("180", "3 Hrs"); put("360", "6 Hrs"); put("720", "12 Hrs");}};


    private static final List<IdName> SETTING_CATEGORIES = new ArrayList<IdName>(){{
        add( new IdName(1L,  "Staff Security") );
        add( new IdName(2L,  "Fraud Management") );
        add( new IdName(3L,  "Consumer Protection & Risk Management") );
        add( new IdName(4L,  "Recurring Payment Service") );
        add( new IdName(5L,  "Maintenance") );
        add( new IdName(6L,  "Contact Information") );
        add( new IdName(7L,  "Action Center") );
        add( new IdName(8L,  "Security Center") );
        add( new IdName(9L,  "Transaction & Settlement Management") );
        add( new IdName(10L, "Extra Switch Settlement Setup") );
        add( new IdName(11L, "Unified Payment Settlement Setup") );
        add( new IdName(12L, "Platform Email Marketing & Advertisement") );
    }};


    private static final List<SettingCache> SETTINGS = new ArrayList<SettingCache>(){{
        add( new SettingCache(category(0), "ad_service", "Active Directory Service",  "no", PAYLOAD_YES_NO,  null) );
        add( new SettingCache(category(0), "enable_login", "Enable Login",  "yes", PAYLOAD_YES_NO,  null) );
        //add( new SettingCache(category(0), "def_active_hour", "Default Active Hour",  "1", activeHourService.options(),  null) );
        add( new SettingCache(category(0), "dormant_period", "Staff Dormant Period in days",  "60", null,  "required|integer") );


        add( new SettingCache(category(1), "max_grp_chx_freq", "Maximum Group Change Frequency Per Day",  "1", null,  "required|integer") );
        add( new SettingCache(category(1), "service_broadcast", "Broadcast Boarded Vendors Service Modifications",  "1", PAYLOAD_YES_ONE,  null) );


        add( new SettingCache(category(2), "max_trans_new", "Daily Amount Limit for New Consumers Users",  "50000", null,  "required|integer") );
        add( new SettingCache(category(2), "max_trans_old", "Daily Amount Limit for Old Consumers Users",  "1000000", null,  "required|integer") );
        add( new SettingCache(category(2), "max_trans_vol_new", "Daily Volume Limit for New Consumer Users",  "2", null,  "required|integer") );
        add( new SettingCache(category(2), "max_trans_vol_old", "Daily Volume Limit for Old Consumer Users",  "10", null,  "required|integer") );
        add( new SettingCache(category(2), "new_csr_thresh", "New consumer threshold by transaction volume",  "5", PAYLOAD_NEW_CSR_THRESH,  null) );


        add( new SettingCache(category(3), "enable_rop", "Recurring Payment Status",  "1", PAYLOAD_ENABLED_ONE,  null) );
        add( new SettingCache(category(3), "stage_rop_notif", "Staged Notifications Before Debit",  "2", PAYLOAD_STAGE_ROP_NOTIF,  null) );
        add( new SettingCache(category(3), "max_rop_retry", "Maximum Transaction Initialization Attempt",  "3", PAYLOAD_MAX_ROP_RETRY,  null) );
        add( new SettingCache(category(3), "rop_fail_delay", "Recurring Payment Failure Delay",  "1", PAYLOAD_ROP_FAIL_DELAY,  null) );


        add( new SettingCache(category(4), "consumer_system_state", "Consumer System State",  "1", PAYLOAD_CONSUMER_SYSTEM_STATE,  null) );
        add( new SettingCache(category(4), "consumer_trans", "Consumer Transactions",  "1", PAYLOAD_CONSUMER_TRANS,  null) );
        add( new SettingCache(category(4), "failure_notification_freq", "Failure Notification Frequency (minutes)",  "15", PAYLOAD_FAILURE_NOTIFICATION_FREQ,  null) );
        add( new SettingCache(category(4), "error_mail_to", "Error Notification Email",  "enenim2000@gmail.com", null,  "required") );


        add( new SettingCache(category(7), "idle_timeout", "Idle Timeout (minutes)",  "30", null,  "required|integer") );
        add( new SettingCache(category(7), "multiple_session", "Allow Multiple Login Session",  "no", PAYLOAD_YES_NO,  null) );

    }};


    public static List<SettingCache> getSettings(){
        return SETTINGS;
    }

    public static Map<String, SettingCache> getSettingMap(){
        Map<String, SettingCache> settings = new HashMap<>();
        SETTINGS.forEach((setting)-> settings.put(setting.getKey(), setting));
        return settings;
    }

    private static IdName category(int position){
        return SETTING_CATEGORIES.get(position);
    }

    public static SettingCache sync(Setting setting){
        SettingCache settingCache = getSettingMap().get(setting.getKey());
        settingCache.setValue(setting.getValue());
        return settingCache;
    }

    public static List<SettingCache> sync(List<Setting> settings){
        List<SettingCache> settingCaches = new ArrayList<>();
        for(Setting setting : settings){
            SettingCache settingCache = getSettingMap().get(setting.getKey());
            settingCache.setValue(setting.getValue());
            settingCaches.add(settingCache);
        }
        return settingCaches;
    }
}