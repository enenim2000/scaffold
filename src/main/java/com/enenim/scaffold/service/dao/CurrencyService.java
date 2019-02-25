package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Currency;
import com.enenim.scaffold.repository.dao.CurrencyRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Page<Currency> getCurrencies(){
        return currencyRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Currency getCurrency(Long id){
        return currencyRepository.findOrFail(id);
    }

    public Currency saveCurrency(Currency currency){
        return currencyRepository.save(currency);
    }

    public void deleteCurrency(Long id){
        currencyRepository.deleteById(id);
    }
}