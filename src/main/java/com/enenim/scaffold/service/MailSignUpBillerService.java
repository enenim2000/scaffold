package com.enenim.scaffold.service;

import com.enenim.scaffold.interfaces.IMailService;
import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.service.cache.SharedExpireCacheService;
import com.enenim.scaffold.shared.Mail;
import com.enenim.scaffold.util.CommonUtil;
import com.enenim.scaffold.util.message.SpringMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSignUpBillerService implements IMailService {
    private final MailService mailService;
    private final SharedExpireCacheService sharedExpireCacheService;

    @Autowired
    public MailSignUpBillerService(MailService mailService, SharedExpireCacheService sharedExpireCacheService) {
        this.mailService = mailService;
        this.sharedExpireCacheService = sharedExpireCacheService;
    }

    @Async
    @Override
    public void send(Object obj){
        Biller biller = (Biller) obj;
        String code = CommonUtil.getCode();
        Mail mail = new Mail();
        mail.setTo(biller.getEmail());
        mail.setSubject(SpringMessage.msg("app_name") + " Registration");
        mail.setTemplate(TEMPLATE_SIGNUP_BILLER);
        mail.getData().put("code", code);
        mail.getData().put("biller", biller);
        String key = SharedExpireCacheService.SINGUP + SharedExpireCacheService.SEPARATOR + code;
        sharedExpireCacheService.put(key, biller);
        mailService.sendMail(mail);
    }
}