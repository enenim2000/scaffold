package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.PaymentChannel;
import com.enenim.scaffold.repository.dao.PaymentChannelRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentChannelService {

    private final PaymentChannelRepository paymentChannelRepository;

    @Autowired
    public PaymentChannelService(PaymentChannelRepository paymentChannelRepository) {
        this.paymentChannelRepository = paymentChannelRepository;
    }

    public Page<PaymentChannel> getPaymentChannels() {
        return paymentChannelRepository.findAll(PageRequestUtil.getPageRequest());
    }
    
    public PaymentChannel getPaymentChannel(Long id) {
        return paymentChannelRepository.findOrFail(id);
    }

    public PaymentChannel savePaymentChannel(PaymentChannel paymentChannel) {
        return paymentChannelRepository.save(paymentChannel);
    }

    public Optional<PaymentChannel> getPaymentChannelByCode(String code) {
        return paymentChannelRepository.findPaymentChannelByCode(code);
    }
}
