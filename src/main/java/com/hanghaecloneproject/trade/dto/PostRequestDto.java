package com.hanghaecloneproject.trade.dto;

import com.hanghaecloneproject.trade.domain.Trade;
import com.hanghaecloneproject.trade.domain.TradeCategory;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto implements Serializable {
    private String title;
    private int price;
    private String content;
    private List<MultipartFile> image;
    private String category;

    public Trade toEntity() {
        return new Trade(title, TradeCategory.valueOf(category), price, content);
    }
}
