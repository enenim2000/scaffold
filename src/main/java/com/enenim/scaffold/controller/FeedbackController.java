package com.enenim.scaffold.controller;

import com.enenim.scaffold.annotation.Get;
import com.enenim.scaffold.annotation.Permission;
import com.enenim.scaffold.annotation.Post;
import com.enenim.scaffold.annotation.Role;
import com.enenim.scaffold.constant.RoleConstant;
import com.enenim.scaffold.dto.request.FeedbackRequest;
import com.enenim.scaffold.dto.request.Request;
import com.enenim.scaffold.dto.request.part.FeedbackReviewRequest;
import com.enenim.scaffold.dto.response.FeedbackResponse;
import com.enenim.scaffold.dto.response.ModelResponse;
import com.enenim.scaffold.dto.response.PageResponse;
import com.enenim.scaffold.dto.response.Response;
import com.enenim.scaffold.model.dao.Feedback;
import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.service.dao.ConsumerService;
import com.enenim.scaffold.service.dao.FeedbackService;
import com.enenim.scaffold.service.dao.VendorService;
import com.enenim.scaffold.shared.FeedbackReview;
import com.enenim.scaffold.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.enenim.scaffold.constant.RouteConstant.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final ConsumerService consumerService;
    private final VendorService vendorService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, ConsumerService consumerService, VendorService vendorService) {
        this.feedbackService = feedbackService;
        this.consumerService = consumerService;
        this.vendorService = vendorService;
    }

    @Get
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_FEEDBACK_INDEX)
    public Response<PageResponse<FeedbackResponse>> getFeedback(){
        return new Response<>(new PageResponse<>(feedbackService.getFeedbackResponses()));
    }

    @Get("/{id}")
    @Role({RoleConstant.STAFF})
    @Permission(ADMINISTRATION_FEEDBACK_SHOW)
    public Response<ModelResponse<FeedbackResponse>> getFeedback(@PathVariable Long id){
        return new Response<>(new ModelResponse<>(feedbackService.getFeedback(id).getFeedbackResponse()));
    }

    @Post
    @Role({RoleConstant.CONSUMER})
    @Permission(ADMINISTRATION_FEEDBACK_CREATE)
    public Response<ModelResponse<Feedback>> createFeedback(@Valid @RequestBody Request<FeedbackRequest> request){
        Feedback feedback = new Feedback();
        feedback.setConsumer( consumerService.getConsumer(request.getBody().getConsumerId()) );
        feedback.setSubject(request.getBody().getSubject());
        feedback.setTransactionReference(request.getBody().getTransactionReference());

        List<FeedbackReview> reviews = new ArrayList<>();

        for(FeedbackReviewRequest review : request.getBody().getReviews()){
            Service service = vendorService.getVendorService(review.getServiceId());
            FeedbackReview feedbackReview = new FeedbackReview();
            feedbackReview.setComment(review.getComment());
            feedbackReview.setRating(review.getRating());
            feedbackReview.setServiceName(service.getName());
            feedbackReview.setServiceId(service.getId());
            reviews.add(feedbackReview);
        }

        feedback.setReview( JsonConverter.getJson(reviews) );

        return new Response<>(new ModelResponse<>(feedbackService.saveFeedback(feedback)));
    }
}
