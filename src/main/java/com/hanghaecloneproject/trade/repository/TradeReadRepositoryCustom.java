package com.hanghaecloneproject.trade.repository;

import com.hanghaecloneproject.trade.domain.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TradeReadRepositoryCustom {

    Page<Trade> findByIdLessThanAndTitleContainingAndAddressContaining(Long id, String title, String address, Pageable pageable);
}
