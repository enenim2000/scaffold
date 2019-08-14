package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.*;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.TicketOpenRequest;
import com.enenim.scaffold.dto.request.part.TicketCommentRequest;
import com.enenim.scaffold.dto.response.CollectionResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.enums.TicketStatus;
import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.model.dao.Ticket;
import com.enenim.scaffold.service.UserResolverService;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.TicketService;
import com.enenim.scaffold.service.dao.VendorService;
import com.enenim.scaffold.shared.TicketHistory;
import com.enenim.scaffold.util.DateUtil;
import com.enenim.scaffold.util.JsonConverter;
import com.enenim.scaffold.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final VendorService vendorService;
    private final TicketService ticketService;
    private final ConsumerService consumerService;
    private final UserResolverService userResolverService;

    @Autowired
    public TicketController(VendorService vendorService, TicketService ticketService, ConsumerService consumerService, UserResolverService userResolverService) {
        this.vendorService = vendorService;
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
    @Role({RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_TICKET_CREATE)
    public Response<ModelResponse<Ticket>> createTicket(@Valid @RequestBody TicketOpenRequest request){

        Long userId = userResolverService.resolveUserId(request.getConsumerId());
        String userType = RequestUtil.getLoginToken().getUserType();

        Ticket ticket = request.buildModel();
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setConsumer( consumerService.getConsumer( userId ) );
        ticket.setTransactionReference(request.getTransactionReference());
        ticket.setComment(request.getComment());
        ticket.setSubject(request.getSubject());

        TicketHistory ticketHistory = new TicketHistory();
        ticketHistory.setComment(request.getComment());
        ticketHistory.setDate(DateUtil.formatDateTime(new Date()));
        ticketHistory.setUserId(userId);
        ticketHistory.setUserType(userType);
        ticketHistory.setServiceId(null);

        ticket.setHistory(JsonConverter.getJson( new ArrayList<TicketHistory>(){{add(ticketHistory);}} ));

        return new Response<>(new ModelResponse<>(ticketService.saveTicket(ticket)));
    }

    @Put("/{id}/comments")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_TICKET_UPDATE)
    public Response<ModelResponse<Ticket>> updateTicket(@PathVariable Long id, @Valid @RequestBody TicketCommentRequest request){

        Ticket ticket = ticketService.getTicket(id);
        Long userId = RequestUtil.getLoginToken().getUserId();
        String userType = RequestUtil.getLoginToken().getUserType();
        String fullname = RequestUtil.getFullname();

        List<TicketHistory> ticketHistories = TicketHistory.getTicketHistories(ticket.getHistory());

        TicketHistory ticketHistory = new TicketHistory();
        ticketHistory.setComment(request.getComment());
        ticketHistory.setDate(DateUtil.formatDateTime(new Date()));
        ticketHistory.setUserId(userId);
        ticketHistory.setUserType(userType);
        ticketHistory.setFullname(fullname);
        ticketHistory.setServiceId(request.getServiceId());

        Service service = vendorService.getVendorService(request.getServiceId());

        ticketHistory.setServiceName(service.getName());
        ticketHistories.add(ticketHistory);

        ticket.setHistory(JsonConverter.getJson(ticketHistories));

        return new Response<>(new ModelResponse<>(ticketService.saveTicket(ticket)));
    }

    @Get("/{id}/services/{service-id}/comments")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_TICKET_SERVICE_COMMENT)
    public Response<CollectionResponse<TicketHistory>> serviceComment(@PathVariable Long id, @PathVariable("service-id") Long serviceId) {
        Ticket ticket = ticketService.getTicket(id);
        List<TicketHistory> ticketHistories = TicketHistory.getTicketHistories(ticket.getHistory());
        List<TicketHistory> serviceComments = new ArrayList<>();

        for (TicketHistory ticketHistory : ticketHistories){
            if(!StringUtils.isEmpty(ticketHistory.getServiceId()) && Objects.equals(ticketHistory.getServiceId(), serviceId)){
                serviceComments.add(ticketHistory);
            }
        }

        return new Response<>(new CollectionResponse<>(serviceComments));
    }

    @Get("/{id}/comments")
    @Role({RoleConstant.STAFF, RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_TICKET_COMMENTS)
    public Response<CollectionResponse<TicketHistory>> serviceComment(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicket(id);
        return new Response<>(new CollectionResponse<>(TicketHistory.getTicketHistories(ticket.getHistory())));
    }
}
