package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.TicketRequest;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.enums.TicketStatus;
import com.enenim.scaffold.model.dao.Ticket;
import com.enenim.scaffold.service.UserResolverService;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.TicketService;
import com.enenim.scaffold.shared.TicketHistory;
import com.enenim.scaffold.util.DateUtil;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final ConsumerService consumerService;
    private final UserResolverService userResolverService;

    @Autowired
    public TicketController(TicketService ticketService, ConsumerService consumerService, UserResolverService userResolverService) {
        this.ticketService = ticketService;
        this.consumerService = consumerService;
        this.userResolverService = userResolverService;
    }

    @Get
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_TICKET_INDEX)
    public Response<PageResponse<Ticket>> getTickets(){
        return new Response<>(new PageResponse<>(ticketService.getTickets()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_TICKET_SHOW)
    public Response<ModelResponse<Ticket>> getTicket(@PathVariable Long id){
        return new Response<>(new ModelResponse<>(ticketService.getTicket(id)));
    }

    @Post
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_TICKET_CREATE)
    public Response<ModelResponse<Ticket>> createTicket(@Valid @RequestBody Request<TicketRequest> request){

        Long userId = userResolverService.resolveUserId(request.getBody().getConsumerId());
        String userType = RequestUtil.getLoginToken().getUserType();

        Ticket ticket = request.getBody().buildModel();
        ticket.setConsumer( consumerService.getConsumer( userId ) );
        ticket.setLastComment(request.getBody().getComment());
        ticket.setLastCommentUserId(userId);
        ticket.setLastCommentUserType(userType);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setTransactionReference(request.getBody().getTransactionReference());

        List<TicketHistory> ticketHistories = new ArrayList<>();

        TicketHistory ticketHistory = new TicketHistory();
        ticketHistory.setComment(request.getBody().getComment());
        ticketHistory.setDate(DateUtil.formatDateTime(new Date()));
        ticketHistory.setUserId(userId);
        ticketHistory.setUserType(userType);
        ticketHistory.setServiceId(request.getBody().getServiceId());
        ticketHistories.add(ticketHistory);

        ticket.setHistory(JsonConverter.getJson(ticketHistories));

        return new Response<>(new ModelResponse<>(ticketService.saveTicket(ticket)));
    }

    @Put("/{id}comment")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_TICKET_UPDATE)
    public Response<ModelResponse<Ticket>> updateTicket(@PathVariable Long id, @Valid @RequestBody Request<TicketRequest> request){

        Ticket ticket = ticketService.getTicket(id);
        Long userId = userResolverService.resolveUserId(request.getBody().getConsumerId());
        String userType = RequestUtil.getLoginToken().getUserType();

        ticket.setLastComment(request.getBody().getComment());
        ticket.setLastCommentUserId(userId);
        ticket.setLastCommentUserType(userType);

        List<TicketHistory> ticketHistories = TicketHistory.getTicketHistories(ticket.getHistory());

        TicketHistory ticketHistory = new TicketHistory();
        ticketHistory.setComment(request.getBody().getComment());
        ticketHistory.setDate(DateUtil.formatDateTime(new Date()));
        ticketHistory.setUserId(userId);
        ticketHistory.setUserType(userType);
        ticketHistory.setServiceId(request.getBody().getServiceId());
        ticketHistories.add(ticketHistory);

        ticket.setHistory(JsonConverter.getJson(ticketHistories));

        return new Response<>(new ModelResponse<>(ticketService.saveTicket(ticket)));
    }
}
