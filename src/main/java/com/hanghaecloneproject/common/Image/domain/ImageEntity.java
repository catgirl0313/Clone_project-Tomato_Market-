package com.hanghaecloneproject.common.Image.domain;

import com.hanghaecloneproject.common.auditing.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ImageEntity extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    // 각 테이블의 key 값
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    public ImageEntity(String imageUrl, ImageType imageType, Long id){
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.objectId = id;
    }
}
