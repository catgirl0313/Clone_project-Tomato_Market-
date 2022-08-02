package com.hanghaecloneproject.post.dto;

import com.hanghaecloneproject.post.domain.Category;
import com.hanghaecloneproject.post.domain.Post;
import com.hanghaecloneproject.post.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private Long postID;
    private String username;
    private String nickname;
    private String profileImage;
    private String address;
    private String title;
    private int price;
    private Category category;
    private String content;
    private Status status;
    private LocalDateTime createdAt;
    private int likeCnt;
    private List<String> imageUrls;

    public PostResponseDto(Post post, List<String> imageUrls) {
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.imageUrls = imageUrls;
        this.category = post.getCategory();
    }

    public PostResponseDto(String title, int price, Category category, List<String> images) {
    }
}
