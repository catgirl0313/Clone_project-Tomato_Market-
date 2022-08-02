package com.hanghaecloneproject.post.domain;

import com.hanghaecloneproject.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Good extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name="POST_ID", nullable = false)
    private Post posts;

}
