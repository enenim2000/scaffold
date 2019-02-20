package com.enenim.scaffold.service;

import com.enenim.scaffold.interfaces.IMailService;
import com.enenim.scaffold.model.dao.Vendor;
import com.enenim.scaffold.service.cache.SharedExpireCacheService;
import com.enenim.scaffold.shared.Mail;
import com.enenim.scaffold.util.CommonUtil;
import com.enenim.scaffold.util.message.SpringMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSignUpVendorService implements IMailService {
    private final MailService mailService;
    private final SharedExpireCacheService sharedExpireCacheService;

    @Autowired
    public MailSignUpVendorService(MailService mailService, SharedExpireCacheService sharedExpireCacheService) {
        this.mailService = mailService;
        this.sharedExpireCacheService = sharedExpireCacheService;
    }

    @Async
    @Override
    public void send(Object obj){
        Vendor vendor = (Vendor) obj;
        String code = CommonUtil.getCode();
        Mail mail = new Mail();
        mail.setTo(vendor.getEmail());
        mail.setSubject(SpringMessage.msg("app_name") + " Registration");
        mail.setTemplate(TEMPLATE_SIGNUP_BILLER);
        mail.getData().put("code", code);
        mail.getData().put("vendor", vendor);
        String key = SharedExpireCacheService.SINGUP + SharedExpireCacheService.SEPARATOR + code;
        sharedExpireCacheService.put(key, vendor);
        mailService.sendMail(mail);
    }
}