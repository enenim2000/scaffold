package com.enenim.scaffold.repository.dao;

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

    @Query("select f from Feedback f where f.consumer.id = ?1")
    Page<Feedback> getConsumerFeedbacks(Long consumerId, Pageable pageable);

    @Query("select f from Feedback f where f.consumer.id = ?1 and f.transactionReference = ?2")
    Optional<Feedback> getConsumerFeedback(Long consumerId, String transactionReference);
}