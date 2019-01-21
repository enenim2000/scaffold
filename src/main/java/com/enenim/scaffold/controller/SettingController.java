package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Permission;
import com.enenim.scaffold.annotation.Put;
import com.enenim.scaffold.annotation.Role;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.SettingRequest;
import com.enenim.scaffold.dto.response.BooleanResponse;
import com.enenim.scaffold.dto.response.CollectionResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.service.dao.SettingService;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.message.CommonMessage;
import com.enenim.scaffold.util.setting.SettingCacheCoreService;
import com.enenim.scaffold.util.setting.SettingCacheCoreUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.enenim.scaffold.constant.RouteConstant.ADMINISTRATION_SETTING_SHOW;
import static com.enenim.scaffold.constant.RouteConstant.ADMINISTRATION_SETTING_SYNC;
import static com.enenim.scaffold.constant.RouteConstant.ADMINISTRATION_SETTING_UPDATE;

@RestController
@RequestMapping("/administration/settings")
public class SettingController {
    private final SettingCacheCoreService settingCacheCoreService;
    private final SettingService settingService;

    public SettingController(SettingCacheCoreService settingCacheCoreService, SettingService settingService) {
        this.settingCacheCoreService = settingCacheCoreService;
        this.settingService = settingService;
    }

    @Get
    public Response<CollectionResponse<SettingCache>> getSettings() {
        return new Response<>(new CollectionResponse<>(SettingCacheCoreUtil.sync(settingService.getSettings())));
    }

    @Get("/{key}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_SHOW)
    public Response<ModelResponse<SettingCache>> getSetting(@PathVariable String key) {
        return new Response<>(new ModelResponse<>(settingCacheCoreService.getCoreSetting(key)));
    }

    @Put
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_UPDATE)
    public Response<ModelResponse<SettingCache>> saveSetting(@RequestBody Request<SettingRequest> request){
        Setting setting = settingService.saveSetting(request.getBody().buildModel());
        return new Response<>(new ModelResponse<>(SettingCacheCoreUtil.sync(setting)));
    }

    @Put("/sync")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_SYNC)
    public Response<BooleanResponse> syncSettings() {
        RequestUtil.setMessage(CommonMessage.msg("setting_sync_success"));
        Map<String, SettingCache> settingCaches = SettingCacheCoreUtil.getSettingMap();
        List<Setting> settings = settingService.getSettings();
        for(Setting setting : settings){
            settingCaches.get(setting.getKey()).setValue(setting.getValue());
        }
        settingCacheCoreService.saveCoreSetting(settingCaches);
        return new Response<>(new BooleanResponse(true));
    }
}