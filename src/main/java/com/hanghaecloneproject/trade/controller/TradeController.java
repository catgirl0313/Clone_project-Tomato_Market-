package com.hanghaecloneproject.trade.controller;

import com.hanghaecloneproject.config.security.dto.UserDetailsImpl;
import com.hanghaecloneproject.user.domain.User;
import com.hanghaecloneproject.trade.dto.PostRequestDto;
import com.hanghaecloneproject.trade.dto.PostCUDResponseDto;
import com.hanghaecloneproject.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    //게시글 생성
    @RequestMapping(value = "/api/post", method = {RequestMethod.POST}, headers = ("Content-Type: multipart/form-data"))
    public ResponseEntity<PostCUDResponseDto> createPost(
            @RequestPart(value = "postDto") PostRequestDto postDto,
            @RequestPart(value = "files") List<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        PostCUDResponseDto responseDto = tradeService.createPost(postDto, files, userDetailsImpl.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
              .body(responseDto);
    }

    //게시글 수정
    @PutMapping("/api/post/{postId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long postId,
            @RequestPart PostRequestDto postDto,
            @RequestPart List<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        tradeService.updatePost(postId, postDto, files, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK)
              .body(null);
    }

    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public Long deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user){
        return tradeService.deletePost(postId, user);
    }

}
