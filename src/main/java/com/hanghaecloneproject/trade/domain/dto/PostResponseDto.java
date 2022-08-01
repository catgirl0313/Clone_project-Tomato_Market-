package com.hanghaecloneproject.trade.domain.dto;

import com.hanghaecloneproject.trade.domain.TradeCategory;
import com.hanghaecloneproject.trade.domain.TradeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private Long postId;
    SellerDto seller;
    private String title;
    private TradeCategory category;
    private int price;
    private String content;
    private TradeStatus status;
    private LocalDateTime createdAt;
    private int like;
    private int view;
    private List<String> image;

}
