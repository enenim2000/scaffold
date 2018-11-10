package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.DisputeMessage;
import com.enenim.scaffold.repository.dao.DisputeMessageRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class DisputeMessageService {

    private final DisputeMessageRepository disputeMessageRepository;

    @Autowired
    public DisputeMessageService(DisputeMessageRepository disputeMessageRepository){
        this.disputeMessageRepository = disputeMessageRepository;
    }

    public Page<DisputeMessage> getDisputeMessages() {
        return disputeMessageRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public DisputeMessage getDisputeMessage(Long id) {
        return disputeMessageRepository.getOne(id);
    }

    public DisputeMessage saveDisputeMessage(DisputeMessage disputeMessage) {
        return disputeMessageRepository.save(disputeMessage);
    }

    public void deleteDisputeMessage(Long id) {disputeMessageRepository.deleteById(id);}

}
