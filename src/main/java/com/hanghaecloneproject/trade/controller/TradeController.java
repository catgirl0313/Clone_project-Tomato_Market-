package com.hanghaecloneproject.trade.controller;

import com.hanghaecloneproject.config.security.dto.UserDetailsImpl;
import com.hanghaecloneproject.trade.dto.PostCUDResponseDto;
import com.hanghaecloneproject.trade.dto.PostRequestDto;
import com.hanghaecloneproject.trade.service.TradeService;
import com.hanghaecloneproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    //게시글 생성
    @PostMapping("/api/post")
    public ResponseEntity<PostCUDResponseDto> createPost(PostRequestDto postDto,
          @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        PostCUDResponseDto responseDto = tradeService.createPost(postDto, userDetailsImpl.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
              .body(responseDto);
    }

    //게시글 수정
    @PutMapping("/api/post/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, PostRequestDto postDto,
          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        tradeService.updatePost(postId, postDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK)
              .body(null);
    }

    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public Long deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        return tradeService.deletePost(postId, user);
    }

}
