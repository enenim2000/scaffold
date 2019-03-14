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
import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.service.dao.SettingService;
import com.enenim.scaffold.util.setting.SettingConfigUtil;
import com.enenim.scaffold.util.setting.SettingListCategory;
import com.enenim.scaffold.util.setting.SystemSetting;
import com.enenim.scaffold.util.setting.SettingCacheService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/administration/settings")
public class SettingController {
    private final SettingCacheService settingCacheService;
    private final SettingService settingService;

    public SettingController(SettingCacheService settingCacheService, SettingService settingService) {
        this.settingCacheService = settingCacheService;
        this.settingService = settingService;
    }

    @Get
    public Response<CollectionResponse<SettingListCategory>> getSettings() {
        return new Response<>(new CollectionResponse<>(settingCacheService.getSystemSettings()));
    }

    @Get("/{key}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_SHOW)
    public Response<ModelResponse<SystemSetting>> getSetting(@PathVariable String key) {
        return new Response<>(new ModelResponse<>(settingCacheService.getSystemSetting(key)));
    }

    @Put
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_UPDATE)
    public Response<ModelResponse<SystemSetting>> saveSetting(@RequestBody Request<SettingRequest> request){
        Setting setting = settingService.saveSetting(request.getBody().buildModel());
        return new Response<>(new ModelResponse<>(settingCacheService.getSystemSetting(setting.getSettingKey())));
    }

    @Put("/{key}/add")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_ADD)
    public Response<ModelResponse<SystemSetting>> addSettings(@PathVariable("key") String key) {
        SystemSetting systemSetting = settingCacheService.getSystemSetting(key);
        Setting setting = settingService.getSetting(key);
        if(StringUtils.isEmpty(setting)){
            setting = new Setting();
        }
        setting.setCategoryKey(systemSetting.getCategoryKey());
        setting.setSettingKey(systemSetting.getSettingKey());
        setting.setValue(systemSetting.getDetail().getValue());
        setting = settingService.saveSetting(setting);
        return new Response<>(new ModelResponse<>(settingCacheService.updateSystemSetting(setting)));
    }

    @Put("/{key}/reset")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_RESET)
    public Response<ModelResponse<SystemSetting>> resetSettings(@PathVariable("key") String key) {
        SystemSetting systemSetting = SettingConfigUtil.getSystemSetting(key);
        Setting setting = settingService.getSetting(key);
        if(StringUtils.isEmpty(setting)){
            setting = new Setting();
            setting.setCategoryKey(systemSetting.getCategoryKey());
            setting.setSettingKey(systemSetting.getSettingKey());
            setting.setValue(systemSetting.getDetail().getValue());
        }else {
            setting.setValue(systemSetting.getDetail().getValue());
        }
        setting = settingService.saveSetting(setting);
        return new Response<>(new ModelResponse<>(settingCacheService.updateSystemSetting(setting)));
    }

    @Put("/sync")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_SYNC)
    public Response<BooleanResponse> syncSettings() {
        return new Response<>(new BooleanResponse(settingCacheService.syncSettings()));
    }
}