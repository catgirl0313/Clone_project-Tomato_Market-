package com.hanghaecloneproject.trade.domain;

import com.hanghaecloneproject.common.auditing.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "like_it",
      indexes = {
            @Index(columnList = "username"),
            @Index(columnList = "tradeId")
      })
@Entity
public class Like extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String username;

    private Long tradeId;

}
