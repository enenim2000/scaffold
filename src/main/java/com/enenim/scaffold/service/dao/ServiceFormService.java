package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.model.dao.ServiceForm;
import com.enenim.scaffold.repository.dao.ServiceFormRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceFormService {
    private final ServiceFormRepository serviceFormRepository;

    @Autowired
    public ServiceFormService(ServiceFormRepository serviceFormRepository) {
        this.serviceFormRepository = serviceFormRepository;
    }

    public Page<ServiceForm> getServiceForms(){
        return serviceFormRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public List<Service> getServiceByReference(String transactionReference){
        return serviceFormRepository.getServiceByReference(transactionReference);
    }

    public ServiceForm getServiceForm(Long id){
        return serviceFormRepository.findOrFail(id);
    }

    public ServiceForm saveServiceForm(ServiceForm serviceForm){
        return serviceFormRepository.save(serviceForm);
    }

    public void deleteServiceForm(Long id){
        serviceFormRepository.deleteById(id);
    }
}