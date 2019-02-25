package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.model.dao.Transaction;
import com.enenim.scaffold.repository.dao.TransactionRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<Transaction> getTransactions(){
        return transactionRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Transaction getTransaction(Long id){
        return transactionRepository.findOrFail(id);
    }

    public Transaction saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id){
        transactionRepository.deleteById(id);
    }
}