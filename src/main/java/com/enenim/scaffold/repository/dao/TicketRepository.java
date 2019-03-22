package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.enums.TicketStatus;
import com.enenim.scaffold.model.dao.Ticket;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TicketRepository extends BaseRepository<Ticket, Long> {

    @Query("select t from Ticket t where t.consumer.id = ?1")
    Page<Ticket> findTicketByConsumerId(Long id, Pageable pageable);

    @Query("select t from Ticket t where t.consumer.id = ?1 and t.status = ?2")
    Page<Ticket> findTicketByConsumerAndStatus(Long id, TicketStatus status, Pageable pageable);
}