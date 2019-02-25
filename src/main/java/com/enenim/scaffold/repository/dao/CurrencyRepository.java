package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Currency;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CurrencyRepository extends BaseRepository<Currency, Long> {

}