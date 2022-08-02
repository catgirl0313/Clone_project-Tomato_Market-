package com.hanghaecloneproject.common.Image.repository;

import com.hanghaecloneproject.common.Image.domain.ImageEntity;
import com.hanghaecloneproject.common.Image.domain.ImageType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    List<ImageEntity> findByImageTypeAndObjectId(ImageType imageType, Long postId);

    void deleteByImageTypeAndObjectId(ImageType imageType, Long postId);

    void deleteAllByImageTypeAndObjectId(ImageType imageType, Long postId);

}
