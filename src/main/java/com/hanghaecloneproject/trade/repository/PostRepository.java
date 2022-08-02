package com.hanghaecloneproject.trade.repository;

import com.hanghaecloneproject.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Trade, Long> {

//    List<Post> findAllByOrderByModifiedAtDesc();

}
