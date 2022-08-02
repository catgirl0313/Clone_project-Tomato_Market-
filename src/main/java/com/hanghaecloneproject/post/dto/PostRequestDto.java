package com.hanghaecloneproject.post.dto;

import com.hanghaecloneproject.post.domain.Category;
import com.hanghaecloneproject.post.domain.ImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {
    private String username;
    private String nickname;
    private String title;
    private int price;
    private List<ImageUrl> imageUrls;
    private String content;
    private Category category;
}
