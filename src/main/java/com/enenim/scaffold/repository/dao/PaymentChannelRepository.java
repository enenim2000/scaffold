package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.PaymentChannel;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PaymentChannelRepository extends BaseRepository<PaymentChannel, Long> {

    @Query("SELECT  new com.enenim.scaffold.shared.IdName(pm.id, pm.name) from PaymentChannel pm")
    List<PaymentChannel> findLists();

    @Query("SELECT pc from PaymentChannel pc where pc.code = ?1")
    Optional<PaymentChannel> findPaymentChannelByCode(String code);
}