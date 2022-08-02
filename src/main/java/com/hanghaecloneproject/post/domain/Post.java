package com.hanghaecloneproject.post.domain;

import com.hanghaecloneproject.User;
import com.hanghaecloneproject.post.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String address; //주소

    @Column(nullable = false)
    private String content;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category; // 카테고리

    @Column
    private Boolean status = false; // 상태

    @OneToMany(mappedBy = "post")
    private List<ImageUrl> imageUrls = new ArrayList<>();

    public Post(User user, String title, int price, String content, Category category) {
        this.user = user;
        this.title = title;
        this.price = price;
        this.content = content;
        this.category = category;
    }

    public void update(PostRequestDto postDto, Category category) {
        this.title = postDto.getTitle();
        this.price = postDto.getPrice();
        this.content = postDto.getContent();
        this.category = category;
    }

    public void addImage(ImageUrl imageUrl) {
        imageUrls.add(imageUrl);
    }
}
