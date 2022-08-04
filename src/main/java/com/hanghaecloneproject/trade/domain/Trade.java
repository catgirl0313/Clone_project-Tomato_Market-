package com.hanghaecloneproject.trade.domain;

import com.hanghaecloneproject.common.auditing.BaseEntity;
import com.hanghaecloneproject.trade.dto.PostRequestDto;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;


@ToString(exclude = "tradeImages")
@Getter
@DynamicUpdate
@Table(name = "trade",
      indexes = {
            @Index(columnList = "title"),
            @Index(columnList = "category"),
            @Index(columnList = "status"),
            @Index(columnList = "address"),
      })
@Entity
public class Trade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TradeCategory category;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TradeStatus status;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TradeImage> tradeImages = new HashSet<>();

    @Column(nullable = false)
    private Long userId;

    @Column(name = "like_it", nullable = false)
    private int like;

    @Column(nullable = false)
    private int view;

    protected Trade() {
    }

    public Trade(String title, TradeCategory category, int price, String content) {
        this.title = title;
        this.category = category;
        this.status = TradeStatus.TRADING;
        this.price = price;
        this.content = content;
        this.like = 0;
        this.view = 0;
    }

    public void settingUserInfo(String address, Long userId) {
        this.address = address;
        this.userId = userId;
    }

    public void addTradeImages(List<TradeImage> tradeImages) {
        for (TradeImage tradeImage : tradeImages) {
            addTradeImage(tradeImage);
        }
    }

    private void addTradeImage(TradeImage tradeImage) {
        tradeImage.setTrade(this);
        this.tradeImages.add(tradeImage);
    }

    public void update(PostRequestDto postRequestDto) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trade trade = (Trade) o;
        return Objects.equals(id, trade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}