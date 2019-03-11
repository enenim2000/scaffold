package com.enenim.scaffold;

import com.enenim.scaffold.service.dao.SettingService;
import com.enenim.scaffold.util.AESUtil;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.SettingConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationRunner {

    private final SettingService settingService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ApplicationStartup(SettingService settingService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.settingService = settingService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //System.out.println("\n Password: " + bCryptPasswordEncoder.encode("Password@123"));
        System.out.println("\n About to load database settings into memory \n");
        SettingConfigUtil.loadDatabaseSettings( settingService.getSettings() );
        System.out.println("\n Loaded database settings into memory \n");

        System.out.println("\n About to sync database settings with configuration settings \n");
        SettingConfigUtil.loadSystemSettings();
        System.out.println("\n Finished syncing database settings with configuration settings \n");

        System.out.println("settings = " + JsonConverter.getJsonRecursive(SettingConfigUtil.getSystemSettings()));

        AESUtil.testEncryption();

    }
}