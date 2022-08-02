package com.hanghaecloneproject.trade.dto;

import com.hanghaecloneproject.trade.domain.TradeCategory;
import com.hanghaecloneproject.trade.domain.Trade;
import com.hanghaecloneproject.trade.domain.TradeStatus;
import com.hanghaecloneproject.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCUDResponseDto {

    private Long postID;
    private Seller seller;
    private String title;
    private int price;
    private TradeCategory tradeCategory;
    private String content;
    private TradeStatus tradeStatus;
    private LocalDateTime createdAt;
    private int likeCnt;
    private List<String> imageUrls;

    public PostCUDResponseDto(Trade trade, User user, List<String> images) {
        this.postID = trade.getId();
        this.seller = new Seller(
              user.getUsername(),
              user.getNickname(),
              user.getProfileImage(),
              user.getAddress());
        this.title = trade.getTitle();
        this.price = trade.getPrice();
        this.tradeCategory = trade.getCategory();
        this.content = trade.getContent();
        this.createdAt = trade.getCreatedAt();
        this.likeCnt = trade.getLike();
        this.imageUrls = images;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Seller {
        private String username;
        private String nickname;
        private String profileImage;
        private String address;
    }


}
