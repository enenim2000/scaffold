package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.ConsumerSetting;
import com.enenim.scaffold.repository.dao.ConsumerSettingRepository;
import com.enenim.scaffold.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ConsumerSettingService{
    private final ConsumerSettingRepository consumerSettingRepository;

    @Autowired
    public ConsumerSettingService(ConsumerSettingRepository consumerSettingRepository) {
        this.consumerSettingRepository = consumerSettingRepository;
    }

    public List<ConsumerSetting> getConsumerSettings(){
        Long consumerId = 0L;
        if(!StringUtils.isEmpty(RequestUtil.getConsumer())){
            consumerId = RequestUtil.getConsumer().getId();
        }
        return consumerSettingRepository.findByConsumerId(consumerId);
    }

    public ConsumerSetting getConsumerSetting(Long id){
        return consumerSettingRepository.findOrFail(id);
    }

    public ConsumerSetting saveConsumerSetting(ConsumerSetting consumerSetting){
        return consumerSettingRepository.save(consumerSetting);
    }
}
