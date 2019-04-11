package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.repository.dao.ServiceRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

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

    public Page<Service> getVendorServices(Long vendorId){
        return serviceRepository.findVendorServices(vendorId, PageRequestUtil.getPageRequest());
    }

    public Service saveVendorService(Service service){
        return serviceRepository.save(service);
    }
}