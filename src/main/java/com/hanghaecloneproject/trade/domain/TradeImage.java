package com.hanghaecloneproject.trade.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class TradeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @Setter
    @ManyToOne
    @JoinColumn(name = "trade_id")
    private Trade trade;

    private boolean isRepresentative;

    protected TradeImage() {
    }

    public TradeImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
