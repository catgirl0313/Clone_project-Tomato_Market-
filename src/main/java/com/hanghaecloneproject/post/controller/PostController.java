package com.hanghaecloneproject.post.controller;

import com.hanghaecloneproject.User;
import com.hanghaecloneproject.post.dto.PostRequestDto;
import com.hanghaecloneproject.post.dto.PostResponseDto;
import com.hanghaecloneproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시글 생성
    @RequestMapping(value = "/api/post", method = {RequestMethod.POST}, headers = ("Content-Type: multipart/form-data"))
    public PostResponseDto createPost(
            @RequestPart(value = "postDto") PostRequestDto postDto,
            @RequestPart(value = "files") List<MultipartFile> files,
            @AuthenticationPrincipal User user)
    {
        PostResponseDto postResponseDto = new PostResponseDto();
        postService.createPost(postDto, files, user);
        return postResponseDto;
    }

    //게시글 수정
    @PutMapping("/api/post/{postId}")
    public PostResponseDto updatePost(
            @PathVariable Long postId,
            @RequestPart PostRequestDto postDto,
            @RequestPart List<MultipartFile> files,
            @AuthenticationPrincipal User user
    ) {
        return postService.updatePost(postId, postDto, files, user);
    }

    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public Long deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user){
        return postService.deletePost(postId, user);
    }

}
