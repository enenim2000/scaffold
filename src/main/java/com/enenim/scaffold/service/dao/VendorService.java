package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.repository.dao.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class VendorService {
    private final ServiceRepository serviceRepository;

    @Autowired
    public VendorService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Service getVendorService(Long id){
        return serviceRepository.findOrFail(id);
    }
}