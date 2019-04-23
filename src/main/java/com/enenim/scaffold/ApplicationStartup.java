package com.enenim.scaffold;

import com.enenim.scaffold.service.dao.SettingService;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.PasswordEncoder;
import com.enenim.scaffold.util.setting.ConsumerSettingConfigUtil;
import com.enenim.scaffold.util.setting.SettingConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationRunner {

    private final SettingService settingService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationStartup(SettingService settingService, PasswordEncoder passwordEncoder) {
        this.settingService = settingService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("Password = " + passwordEncoder.encode("Password@123"));

        System.out.println("\n About to load database settings into memory \n");
        SettingConfigUtil.loadDatabaseSettings( settingService.getSettings() );
        System.out.println("\n Loaded database settings into memory \n");

        System.out.println("\n About to sync database settings with configuration settings \n");
        SettingConfigUtil.loadSystemSettings();
        System.out.println("\n Finished syncing database settings with configuration settings \n");

        System.out.println("settings = " + JsonConverter.getJsonRecursive(SettingConfigUtil.getSystemSettings()));

        System.out.println("\n About to load consumer setting config into memory \n");
        ConsumerSettingConfigUtil.loadConsumerSystemSettings();
        System.out.println("\n Finished loading consumer setting config into memory \n");

    }
}