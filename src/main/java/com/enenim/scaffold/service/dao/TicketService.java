package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.enums.TicketStatus;
import com.enenim.scaffold.model.dao.Ticket;
import com.enenim.scaffold.repository.dao.TicketRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Page<Ticket> getTickets(){
        return ticketRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Page<Ticket> getTickets(Long consumerId){
        return ticketRepository.findTicketByConsumerId(consumerId, PageRequestUtil.getPageRequest());
    }

    public Page<Ticket> getTickets(Long consumerId, TicketStatus status){
        return ticketRepository.findTicketByConsumerAndStatus(consumerId, status, PageRequestUtil.getPageRequest());
    }

    public Ticket getConsumerTicket(Long consumerId, Long ticketId){
        return ticketRepository.findTicketByIdAndConsumerId(consumerId, ticketId);
    }

    public Ticket getTicket(Long id){
        return ticketRepository.findOrFail(id);
    }

    public Ticket saveTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }
}
