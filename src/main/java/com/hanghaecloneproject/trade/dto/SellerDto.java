package com.hanghaecloneproject.trade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDto {
    private String nickname;
    private String profile;
    private String address;

    public SellerDto(String nickname, String profile, String address) {
        this.nickname = nickname;
        this.profile = profile;
        this.address = address;

    }
}
