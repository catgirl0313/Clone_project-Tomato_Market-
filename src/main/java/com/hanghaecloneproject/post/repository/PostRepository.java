package com.hanghaecloneproject.post.repository;

import com.hanghaecloneproject.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    List<Post> findAllByOrderByModifiedAtDesc();

}