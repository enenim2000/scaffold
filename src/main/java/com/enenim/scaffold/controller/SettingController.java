package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Permission;
import com.enenim.scaffold.annotation.Put;
import com.enenim.scaffold.annotation.Role;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.SettingRequest;
import com.enenim.scaffold.dto.request.SettingResetRequest;
import com.enenim.scaffold.dto.response.BooleanResponse;
import com.enenim.scaffold.dto.response.CollectionResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.service.dao.SettingService;
import com.enenim.scaffold.util.setting.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

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
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_INDEX)
    @ApiOperation(value = "Get Categorized Settings",
            notes = "This endpoint retrieved all settings grouped by category and as a list")
    public CollectionResponse<SettingListCategory> getSettings() {
        return new CollectionResponse<>(settingCacheService.getSystemSettings());
    }

    @Get("/{key}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_SHOW)
    @ApiOperation(value = "Get Setting",
            notes = "This endpoint retrieved setting by Key")
    public ModelResponse<SystemSetting> getSetting(@PathVariable String key) {
        return new ModelResponse<>(settingCacheService.getSystemSetting(key));
    }

    @Put
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_UPDATE)
    @ApiOperation(value = "Update Setting",
            notes = "This endpoint update a setting")
    public ModelResponse<SystemSetting> saveSetting(@Valid @RequestBody SettingRequest request){
        System.out.println("setting1: " + request.buildModel());
        Setting setting = settingService.getSetting(request.getSettingKey());
        setting = settingService.saveSetting(request.buildModel(setting));
        System.out.println("setting2: " + setting);
        return new ModelResponse<>(settingCacheService.updateSystemSetting(setting));
    }

    @PutMapping("/{key}/add")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_ADD)
    @ApiOperation(value = "Add new Setting",
            notes = "This endpoint add a new setting")
    public ModelResponse<SystemSetting> addSettings(@PathVariable("key") String key) {
        SystemSetting systemSetting = settingCacheService.getSystemSetting(key);
        Setting setting = settingService.getSetting(key);
        if(StringUtils.isEmpty(setting)){
            setting = new Setting();
        }
        setting.setCategoryKey(systemSetting.getCategoryKey());
        setting.setSettingKey(systemSetting.getSettingKey());
        setting.setValue(systemSetting.getDetail().getValue());
        setting = settingService.saveSetting(setting);
        return new ModelResponse<>(settingCacheService.updateSystemSetting(setting));
    }

    @PutMapping("/reset")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_RESET)
    @ApiOperation(value = "Reset Setting",
            notes = "This endpoint reset a setting to default")
    public ModelResponse<SystemSetting> resetSettings(@Valid @RequestBody SettingResetRequest request) {
        SystemSetting systemSetting = SettingConfigUtil.getConfiguredSystemSetting(request.getCategoryKey(), request.getSettingKey());
        Setting setting = settingService.getSetting(systemSetting.getSettingKey());
        if(StringUtils.isEmpty(setting)){
            setting = new Setting();
            setting.setCategoryKey(systemSetting.getCategoryKey());
            setting.setSettingKey(systemSetting.getSettingKey());
            setting.setValue(systemSetting.getDetail().getValue());
        }else {
            setting.setValue(systemSetting.getDetail().getValue());
        }
        setting = settingService.saveSetting(setting);
        return new ModelResponse<>(settingCacheService.updateSystemSetting(setting));
    }

    @PutMapping("/sync")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_SYNC)
    @ApiOperation(value = "Sync Setting",
            notes = "This endpoint sync database with config settings")
    public BooleanResponse syncSettings() {
        return new BooleanResponse(settingCacheService.syncSettings());
    }

    @Get("/categories/{key}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_CATEGORY)
    @ApiOperation(value = "Get Settings",
            notes = "This endpoint retrieved settings for a category and group the settings as a map")
    public ModelResponse<SettingMapCategory> getSettingsByCategory(@PathVariable("key") String key) {
        return new ModelResponse<>(settingCacheService.getSettingsByCategory(key));
    }

    @Get("/categorized")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_SETTING_CATEGORIES)
    @ApiOperation(value = "Get All Settings",
            notes = "This endpoint retrieved all settings grouped by category and as a map")
    public ModelResponse<HashMap<String, SettingMapCategory>> getCategorizedSettings() {
        return new ModelResponse<>(settingCacheService.getCategorizedSettings());
    }
}