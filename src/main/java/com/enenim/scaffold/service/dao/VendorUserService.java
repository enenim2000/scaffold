package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.dto.request.VendorProfileRequest;
import com.enenim.scaffold.model.dao.Vendor;
import com.enenim.scaffold.repository.dao.VendorRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class VendorUserService extends BaseModelService<VendorProfileRequest>{
    private final VendorRepository vendorRepository;

    @Autowired
    public VendorUserService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Page<Vendor> getVendors(){
        return vendorRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Vendor getVendor(Long id){
        return vendorRepository.findOrFail(id);
    }

    public Vendor saveVendor(Vendor vendor){
        return vendorRepository.save(vendor);
    }

    public void deleteVendor(Long id){
        vendorRepository.deleteById(id);
    }

    public Vendor getVendorByEmail(String email) {
        return vendorRepository.findByEmail(email).orElse(null);
    }

    public Object toggle(Long id) {
        return vendorRepository.toggle(id);
    }

    @Override
    public void validateDependencies(VendorProfileRequest request) {

    }
}
