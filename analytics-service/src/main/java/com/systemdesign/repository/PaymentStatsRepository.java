package com.systemdesign.repository;

import com.systemdesign.model.PaymentStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStatsRepository extends JpaRepository<PaymentStats, Long> {
}
