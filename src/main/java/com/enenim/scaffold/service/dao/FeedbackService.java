package com.enenim.scaffold.service.dao;

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

    public Page<Feedback> getFeedbacks(){
        return feedbackRepository.findAll(PageRequestUtil.getPageRequest());
    }

    public Feedback getFeedback(Long id){
        return feedbackRepository.findOrFail(id);
    }

    public Page<Feedback> getConsumerFeedback(Long consumerId){
        return feedbackRepository.findOrFail(consumerId);
    }

    public Feedback saveFeedback(Feedback feedback){
        return feedbackRepository.save(feedback);
    }
}
