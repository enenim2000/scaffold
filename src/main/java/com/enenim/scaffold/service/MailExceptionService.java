package com.enenim.scaffold.service;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.exception.ScaffoldException;
import com.enenim.scaffold.interfaces.IMailService;
import com.enenim.scaffold.shared.Mail;
import com.enenim.scaffold.util.ErrorUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailExceptionService  implements IMailService {
    private final MailService mailService;

    @Autowired
    public MailExceptionService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    @Async
    public void send(Object obj){
        Exception exception = new Exception();
        Mail mail = new Mail();
        mail.setTemplate(TEMPLATE_EXCEPTION);
        mail.setSubject("Exception");
        mail.setTo("enenim2000@yahoo.com");
        mail.setFrom("noreply@glocurex.com");
        String begin = "\n\n{} details begins";
        String end = "{} details ends\n\n";
        if(obj instanceof NullPointerException){
            begin = begin.replace(CommonConstant.PLACE_HOLDER, NullPointerException.class.getSimpleName());
            end = end.replace(CommonConstant.PLACE_HOLDER, NullPointerException.class.getSimpleName());
            exception = (NullPointerException)obj;
        }else if(obj instanceof ConstraintViolationException){
            begin = begin.replace(CommonConstant.PLACE_HOLDER, ConstraintViolationException.class.getSimpleName());
            end = end.replace(CommonConstant.PLACE_HOLDER, ConstraintViolationException.class.getSimpleName());
            exception = (ConstraintViolationException)obj;
        }else if(obj instanceof ScaffoldException){
            begin = begin.replace(CommonConstant.PLACE_HOLDER, ScaffoldException.class.getSimpleName());
            end = end.replace(CommonConstant.PLACE_HOLDER, ScaffoldException.class.getSimpleName());
            exception = (ScaffoldException)obj;
        }

        System.out.println(begin);
        List<String> errors = new ArrayList<>();
        mail.getData().put("begin", begin);
        mail.getData().put("end", end);
        mail.getData().put("message", "");
        mail.getData().put("errors", errors);

        if(exception.getCause() != null){
            System.out.println(exception.getCause().getMessage());
            mail.getData().put("message", exception.getCause().getMessage());
            mail.setSubject(exception.getCause().getMessage());
        }
        for(String errorTrace : ErrorUtil.getErrorTrace(exception.getStackTrace())){
            System.out.println(errorTrace);
            errors.add(errorTrace);
        }
        mail.getData().put("errors", errors);
        System.out.println(end);
        System.out.println("Exception mail getTo be sent");
        mailService.sendMail(mail);
        System.out.println("Exception mail sent successfully");
    }
}