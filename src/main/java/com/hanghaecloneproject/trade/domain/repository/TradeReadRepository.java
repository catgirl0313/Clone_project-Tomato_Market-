package com.hanghaecloneproject.trade.domain.repository;

import com.hanghaecloneproject.trade.domain.Trade;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeReadRepository extends JpaRepository<Trade, Long> {
    Slice<Trade> findByIdGreaterThanAndTitleContainingAndAddressOrderByIdDesc(Long id, String title, String address, Pageable pageable);
}
