package com.hanghaecloneproject.trade.dto;

import com.hanghaecloneproject.trade.domain.Trade;
import com.hanghaecloneproject.trade.domain.TradeCategory;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto implements Serializable {
    private String title;
    private int price;
    private String content;
    private TradeCategory tradeCategory;

    public Trade toEntity() {
        return new Trade(title, tradeCategory, price, content);
    }
}
