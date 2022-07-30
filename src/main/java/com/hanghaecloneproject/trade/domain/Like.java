package com.hanghaecloneproject.trade.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

// 상품 좋아요

@Table(name = "like_it",
      indexes = {
            @Index(columnList = "username")
      })
@Entity
public class Like {

    @Id
    private Long tradeId;

    private Long username;

}
