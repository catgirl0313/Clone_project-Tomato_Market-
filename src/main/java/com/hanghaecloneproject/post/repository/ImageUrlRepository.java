package com.hanghaecloneproject.post.repository;

import com.hanghaecloneproject.post.domain.ImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ImageUrlRepository extends JpaRepository<ImageUrl, Long> {

    List<ImageUrl> findByPostId(Long postId);

    @Transactional
    void deleteAllByPostId(Long postId);

}
