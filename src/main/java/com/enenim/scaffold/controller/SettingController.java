package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Put;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.SettingRequest;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.cache.SettingCache;
import com.enenim.scaffold.model.dao.Setting;
import com.enenim.scaffold.service.dao.SettingService;
import com.enenim.scaffold.util.setting.SettingCacheCoreService;
import com.enenim.scaffold.util.setting.SettingCacheCoreUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administration/settings")
public class SettingController {
    private final SettingCacheCoreService settingCacheCoreService;
    private final SettingService settingService;

    public SettingController(SettingCacheCoreService settingCacheCoreService, SettingService settingService) {
        this.settingCacheCoreService = settingCacheCoreService;
        this.settingService = settingService;
    }

    @Get("/{key}")
    public Response<ModelResponse<SettingCache>> getSetting(@PathVariable String key) {
        return new Response<>(new ModelResponse<>(settingCacheCoreService.getCoreSetting(key)));
    }

    @Put
    public Response<ModelResponse<SettingCache>> saveSetting(@RequestBody Request<SettingRequest> request){
        request.getBody().validateRequest();
        Setting setting = settingService.saveSetting(request.getBody().buildModel());
        return new Response<>(new ModelResponse<>(SettingCacheCoreUtil.sync(setting)));
    }
}