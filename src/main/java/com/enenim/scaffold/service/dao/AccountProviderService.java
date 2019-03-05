package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.AccountProvider;
import com.enenim.scaffold.repository.dao.AccountProviderRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AccountProviderService {
    private final AccountProviderRepository accountProviderRepository;

    @Autowired
    public AccountProviderService(AccountProviderRepository accountProviderRepository) {
        this.accountProviderRepository = accountProviderRepository;
    }

    public Page<AccountProvider> getAccountProviders(){
        return accountProviderRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public AccountProvider getAccountProvider(Long id){
        return accountProviderRepository.findOrFail(id);
    }
    
    public AccountProvider saveAccountProvider(AccountProvider accountProvider){
        return accountProviderRepository.save(accountProvider);
    }

    public void deleteAccountProvider(Long id){
        accountProviderRepository.deleteById(id);
    }
}