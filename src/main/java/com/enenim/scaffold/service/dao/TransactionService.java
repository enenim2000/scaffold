package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.dto.request.TransactionFilterRequest;
import com.enenim.scaffold.enums.TransactionStatus;
import com.enenim.scaffold.model.dao.Transaction;
import com.enenim.scaffold.repository.dao.TransactionRepository;
import com.enenim.scaffold.util.DateUtil;
import com.enenim.scaffold.util.PageRequestUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

;

@org.springframework.stereotype.Service
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

    public Page<Transaction> getConsumerTransactions(TransactionFilterRequest filter, Long consumerId){
        TransactionFilter transactionFilter = new TransactionFilter(filter);
        if(StringUtils.isEmpty(transactionFilter.getSearchTerm())){
            return transactionRepository.findConsumerTransactions( transactionFilter.getStartDate(), transactionFilter.getEndDate(), transactionFilter.getStatuses(), consumerId, PageRequestUtil.getPageRequest() );
        }else{
            return transactionRepository.findConsumerTransactionsBySearchTerm( transactionFilter.getStartDate(), transactionFilter.getEndDate(), transactionFilter.getStatuses(), transactionFilter.getSearchTerm(), consumerId, PageRequestUtil.getPageRequest() );
        }
    }

    @Data
    private class TransactionFilter {
        private Date startDate;
        private Date endDate;
        private List<TransactionStatus> statuses;
        private String searchTerm;

        TransactionFilter(TransactionFilterRequest filter){

            if(StringUtils.isEmpty(filter.getStatus())){
                setStatuses(new ArrayList<TransactionStatus>(){{add(TransactionStatus.FAILED); add(TransactionStatus.PAID); add(TransactionStatus.PENDING); add(TransactionStatus.REVERSED);}});
            }else {
                setStatuses(new ArrayList<TransactionStatus>(){{add(filter.getStatus());}});
            }

            if(StringUtils.isEmpty(startDate)){
                setStartDate( DateUtil.parse("0000-00-00 00:00:00") );
            }else {
                setStartDate( DateUtil.parse(filter.getStartDate()) );
            }

            if(StringUtils.isEmpty(endDate)){
                setEndDate( new Date() );
            }else {
                setEndDate( DateUtil.parse(filter.getEndDate()) );
            }

            if(StringUtils.isEmpty(searchTerm)){
                setSearchTerm("");
            } else {
                setSearchTerm( filter.getSearchTerm() );
            }

        }
    }
}