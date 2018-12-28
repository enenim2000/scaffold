package com.enenim.scaffold.service;

import com.enenim.scaffold.exception.UnAuthorizedException;
import com.enenim.scaffold.shared.Channel;
import com.enenim.scaffold.util.message.SpringMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiKeyService {
    private Map<String, Channel> API_KEY_CONFIG = new HashMap<String, Channel>() {{
        put("API_KEY_ANDROID",  new Channel("001", "Scaffold Android"));
        put("API_KEY_ADMIN",  new Channel("002", "Scaffold Admin"));
        put("API_KEY_WEB",  new Channel("003", "Scaffold Web"));
        put("API_KEY_IOS", new Channel("004", "Scaffold IOS"));
    }};

    private Channel validateApiKey(String apiKeyRequestValue, Map<String, Channel> apiKeyConfig){
        String apiConfigValue;
        for(Map.Entry<String, Channel> entry : apiKeyConfig.entrySet()){
            apiConfigValue = SpringMessage.msg(entry.getKey());
            if(apiConfigValue.equalsIgnoreCase(apiKeyRequestValue)){
                entry.getValue().setKey(entry.getKey());
                entry.getValue().setValue(apiConfigValue);
                return entry.getValue();
            }
        }
        throw new UnAuthorizedException("invalid_apikey");
    }

    public Channel validateKey(String apiKeyRequestValue){
        return validateApiKey(apiKeyRequestValue, API_KEY_CONFIG);
    }

    public Channel getChannel(String apiKey){
        return validateKey(apiKey);
    }
}