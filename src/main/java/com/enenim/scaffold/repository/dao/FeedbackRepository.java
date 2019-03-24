package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.dto.response.FeedbackResponse;
import com.enenim.scaffold.model.dao.Feedback;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface FeedbackRepository extends BaseRepository<Feedback, Long> {

    @Query("select new FeedbackResponse(f) from Feedback f")
    Page<FeedbackResponse> getFeedbacks(Pageable pageable);

    @Query("select new FeedbackResponse(f) from Feedback f where f.consumer.id = ?1")
    Page<FeedbackResponse> getConsumerFeedbacks(Long consumerId, Pageable pageable);

    @Query("select new FeedbackResponse(f) from Feedback f where f.consumer.id = ?1 and f.id = ?2")
    Optional<FeedbackResponse> getConsumerFeedback(Long consumerId, Long feedbackId);

    @Query("select f from Feedback f where f.consumer.id = ?1 and f.transactionReference = ?2")
    Optional<Feedback> getConsumerFeedback(Long consumerId, String transactionReference);
}