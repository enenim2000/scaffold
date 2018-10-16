package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Consumer;
import com.enenim.scaffold.repository.dao.ConsumerRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {
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

    public Consumer saveConsumer(Consumer consumer){
        return consumerRepository.save(consumer);
    }

    public void deleteConsumer(Long id){
        consumerRepository.deleteById(id);
    }

    public List<Consumer> searchConsumer(){
        return consumerRepository.search(PageRequestUtil.getPageRequest());
    }

    public Object toggle(Long id) {
        return consumerRepository.toggle(id);
    }
}
