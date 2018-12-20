package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.dto.request.BillerRequest;
import com.enenim.scaffold.model.dao.Biller;
import com.enenim.scaffold.repository.dao.BillerRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BillerService extends BaseModelService<BillerRequest>{
    private final BillerRepository billerRepository;

    @Autowired
    public BillerService(BillerRepository billerRepository) {
        this.billerRepository = billerRepository;
    }

    public Page<Biller> getBillers(){
        return billerRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Biller getBiller(Long id){
        return billerRepository.findOrFail(id);
    }

    public Biller saveBiller(Biller biller){
        return billerRepository.save(biller);
    }

    public void deleteBiller(Long id){
        billerRepository.deleteById(id);
    }

    public Biller getBillerByEmail(String email) {
        return billerRepository.findByEmail(email).orElse(null);
    }

    public Object toggle(Long id) {
        return billerRepository.toggle(id);
    }

    @Override
    public void validateDependencies(BillerRequest request) {

    }
}
