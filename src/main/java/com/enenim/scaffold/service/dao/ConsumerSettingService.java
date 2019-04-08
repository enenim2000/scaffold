package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.model.dao.ConsumerSetting;
import com.enenim.scaffold.repository.dao.ConsumerSettingRepository;
import com.enenim.scaffold.service.UserResolverService;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.setting.ConsumerSettingConfigUtil;
import com.enenim.scaffold.util.setting.ConsumerSettingMapCategory;
import com.enenim.scaffold.util.setting.ConsumerSystemSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ConsumerSettingService{
    private final ConsumerSettingRepository consumerSettingRepository;
    private final UserResolverService userResolverService;

    @Autowired
    public ConsumerSettingService(ConsumerSettingRepository consumerSettingRepository, UserResolverService userResolverService) {
        this.consumerSettingRepository = consumerSettingRepository;
        this.userResolverService = userResolverService;
    }

    public List<ConsumerSetting> getConsumerSettings(Long id){
        return consumerSettingRepository.findByConsumerId(userResolverService.resolveUserId(id));
    }

    public ConsumerSetting getConsumerSetting(Long id){
        return consumerSettingRepository.findOrFail(id);
    }

    public ConsumerSetting saveConsumerSetting(ConsumerSetting consumerSetting){
        return consumerSettingRepository.save(consumerSetting);
    }

    @Async
    public void saveConsumerSettings(List<ConsumerSetting> consumerSettings){
        consumerSettingRepository.saveAll(consumerSettings);
    }

    public List<ConsumerSetting> getConsumerSettings(Consumer consumer){
        HashMap<String, ConsumerSettingMapCategory> categoryMap = ConsumerSettingConfigUtil.getMemoryConsumerSettings();
        List<ConsumerSetting> consumerSettings = new ArrayList<>();
        for(HashMap.Entry<String, ConsumerSettingMapCategory> categoryEntry : categoryMap.entrySet()){
            for(HashMap.Entry<String, ConsumerSystemSetting> settingEntry : categoryEntry.getValue().getSettings().entrySet()){
                ConsumerSetting consumerSetting = new ConsumerSetting();
                consumerSetting.setConsumer(consumer);
                consumerSetting.setCategoryKey(categoryEntry.getKey());
                consumerSetting.setSettingKey(settingEntry.getKey());
                consumerSetting.setValue(settingEntry.getValue().getDetail().getValue());
                consumerSettings.add(consumerSetting);
            }
        }

        System.out.println("JsonConverter.getJson(consumerSettings = " + JsonConverter.getJson(consumerSettings));

        return consumerSettings;
    }

    public List<ConsumerSystemSetting> getConsumerSystemSettings(Long consumerId){
        List<ConsumerSetting> consumerSettings = getConsumerSettings(consumerId);

        HashMap<String, ConsumerSettingMapCategory> categoryMap = ConsumerSettingConfigUtil.getMemoryConsumerSettings();

        List<ConsumerSystemSetting> consumerSystemSettings =  new ArrayList<>();

        for(ConsumerSetting consumerSetting : consumerSettings){
            ConsumerSystemSetting consumerSystemSetting = categoryMap.get(consumerSetting.getCategoryKey()).getSettings().get(consumerSetting.getSettingKey());
            if(!StringUtils.isEmpty(consumerSystemSetting)){
                consumerSystemSetting.getDetail().setValue( consumerSetting.getValue() );
            }
            consumerSystemSettings.add(consumerSystemSetting);
        }

        return consumerSystemSettings;
    }

    public HashMap<String, ConsumerSystemSetting> getConsumerSystemSettingsMap(Long consumerId){
        List<ConsumerSetting> consumerSettings = getConsumerSettings(consumerId);

        HashMap<String, ConsumerSettingMapCategory> categoryMap = ConsumerSettingConfigUtil.getMemoryConsumerSettings();

        HashMap<String, ConsumerSystemSetting> consumerSystemSettings =  new HashMap<>();

        for(ConsumerSetting consumerSetting : consumerSettings){
            ConsumerSystemSetting consumerSystemSetting = categoryMap.get(consumerSetting.getCategoryKey()).getSettings().get(consumerSetting.getSettingKey());
            if(!StringUtils.isEmpty(consumerSystemSetting)){
                consumerSystemSetting.getDetail().setValue( consumerSetting.getValue() );
            }
            consumerSystemSettings.put(consumerSetting.getSettingKey(), consumerSystemSetting);
        }

        return consumerSystemSettings;
    }

    public ConsumerSystemSetting getConsumerSystemSetting(Long consumerId, String settingKey){
        ConsumerSetting consumerSetting = consumerSettingRepository.findByConsumerIdAndKey(consumerId, settingKey);
        ConsumerSystemSetting  consumerSystemSetting = ConsumerSettingConfigUtil.getMemoryConsumerSettings().get(consumerSetting.getCategoryKey()).getSettings().get(consumerSetting.getSettingKey());
        if(!StringUtils.isEmpty(consumerSystemSetting)){
            consumerSystemSetting.getDetail().setValue( consumerSetting.getValue() );
        }
        return consumerSystemSetting;
    }
}
