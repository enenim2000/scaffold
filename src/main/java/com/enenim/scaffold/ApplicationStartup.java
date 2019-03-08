package com.enenim.scaffold;

import com.enenim.scaffold.service.dao.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationRunner {

    private final SettingService settingService;

    @Autowired
    public ApplicationStartup(SettingService settingService) {
        this.settingService = settingService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\n About to load database settings into memory \n");
        //SettingConfigUtil.updateSettings( settingService.getSettings() );
        System.out.println("\n Loaded database settings into memory \n");

        System.out.println("\n About to sync database settings with configuration settings \n");
        //SettingConfigUtil.loadSettings();
        System.out.println("\n Finished syncing database settings with configuration settings \n");
    }
}