package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.dto.request.ConsumerRequest;
import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.repository.dao.ConsumerRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService extends BaseModelService<ConsumerRequest>{
    private final ConsumerRepository consumerRepository;

    @Autowired
    public ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public Page<Consumer> getConsumers(){
        return consumerRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Consumer getConsumer(Long id){
        return consumerRepository.findOrFail(id);
    }

    public Consumer getConsumerByEmail(String email) {
        return consumerRepository.findByEmail(email).orElse(null);
    }

    public Consumer saveConsumer(Consumer consumer){
        return consumerRepository.save(consumer);
    }

    public Object toggle(Long id) {
        return consumerRepository.toggle(id);
    }

    @Override
    public void validateDependencies(ConsumerRequest request) {

    }
}
