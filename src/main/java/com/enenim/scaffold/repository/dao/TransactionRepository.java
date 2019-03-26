package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.enums.TransactionStatus;
import com.enenim.scaffold.model.dao.Transaction;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends BaseRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.createdAt between ?1 and ?2 and t.status in ?3 and t.consumer.id = ?4")
    Page<Transaction> findConsumerTransactions(Date startDate, Date endDate, List<TransactionStatus> statuses, Long userId, Pageable pageable);

    @Query("select t from Transaction t where t.createdAt between ?1 and ?2 and t.status in ?3 and (t.createdAt like ?4 or t.consumer.firstName like ?4 or t.consumer.lastName like ?4) and t.consumer.id = ?5")
    Page<Transaction> findConsumerTransactionsBySearchTerm(Date startDate, Date endDate, List<TransactionStatus> statuses, String searchTerm, Long userId, Pageable pageable);

    @Query("select t from Transaction t join t.serviceForms sf where t.createdAt between ?1 and ?2 and t.status in ?3 and sf.service.vendor.id = ?4")
    Page<Transaction> findVendorTransactions(Date startDate, Date endDate, List<TransactionStatus> statuses, Long userId, Pageable pageable);

    @Query("select t from Transaction t join t.serviceForms sf where t.createdAt between ?1 and ?2 and t.status in ?3 and (t.createdAt like ?4 or sf.service.vendor.tradingName like ?4 or sf.service.vendor.tradingName like ?4) and sf.service.vendor.id = ?5")
    Page<Transaction> findVendorTransactionsBySearchTerm(Date startDate, Date endDate, List<TransactionStatus> statuses, String searchTerm, Long userId, Pageable pageable);

}
