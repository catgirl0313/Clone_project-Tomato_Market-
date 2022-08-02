package com.hanghaecloneproject.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String name;

}
