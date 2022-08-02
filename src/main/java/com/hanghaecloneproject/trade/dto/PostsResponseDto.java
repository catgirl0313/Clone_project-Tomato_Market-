package com.hanghaecloneproject.trade.dto;

import com.hanghaecloneproject.trade.domain.TradeCategory;
import com.hanghaecloneproject.trade.domain.TradeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostsResponseDto {
    private Long postId;
    private String title;
    private String address;
    private int price;
    private String image;
    private TradeCategory category;
    private TradeStatus status;
    private LocalDateTime createdAt;
    private int like;
    private int view;


}
