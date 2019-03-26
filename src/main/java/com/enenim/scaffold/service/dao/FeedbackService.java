package com.enenim.scaffold.service.dao;

import com.enenim.scaffold.dto.response.FeedbackResponse;
import com.enenim.scaffold.model.dao.Feedback;
import com.enenim.scaffold.repository.dao.FeedbackRepository;
import com.enenim.scaffold.util.PageRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Page<FeedbackResponse> getFeedbackResponses(){
        return feedbackRepository.getFeedbacks(PageRequestUtil.getPageRequest());
    }

    public Feedback getFeedback(Long id){
        return feedbackRepository.findOrFail(id);
    }

<<<<<<< HEAD
    public Page<Feedback> getConsumerFeedback(Long consumerId){
        return feedbackRepository.findOrFail(consumerId);
=======
    public Page<FeedbackResponse> getConsumerFeedbackResponses(Long consumerId){
        return feedbackRepository.getConsumerFeedbacks(consumerId, PageRequestUtil.getPageRequest());
    }

    public FeedbackResponse getConsumerFeedback(Long consumerId, Long feedbackId){
        return feedbackRepository.getConsumerFeedback(consumerId, feedbackId).orElse(null);
>>>>>>> 98bb1336b34a3f9f7afac2da65661e88f3acda99
    }

    public Feedback saveFeedback(Feedback feedback){
        return feedbackRepository.save(feedback);
    }
}
