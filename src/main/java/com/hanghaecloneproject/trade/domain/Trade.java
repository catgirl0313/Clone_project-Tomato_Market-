package com.hanghaecloneproject.trade.domain;

import com.hanghaecloneproject.common.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@DynamicUpdate
@Table(name = "trade",
      indexes = {
            @Index(columnList = "title")
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

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int like;

    @Column(nullable = false)
    private int view;

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
