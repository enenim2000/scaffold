package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.dto.response.TicketResponse;
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

    @Query("select distinct new com.enenim.scaffold.dto.response.TicketResponse(t) from Ticket t where t.consumer.id = ?1")
    Page<TicketResponse> findTicketByConsumerId(Long id, Pageable pageable);

    @Query("select distinct new com.enenim.scaffold.dto.response.TicketResponse(t) from Ticket t where t.consumer.id = ?1 and t.status = ?2")
    Page<TicketResponse> findTicketByConsumerAndStatus(Long id, TicketStatus status, Pageable pageable);

    @Query("select new com.enenim.scaffold.dto.response.TicketResponse(t) from Ticket t where t.id = ?2 and t.consumer.id = ?1")
    TicketResponse findTicketByIdAndConsumerId(Long id, Long ticketId);
}