package com.hanghaecloneproject.trade.domain.controller;

import com.hanghaecloneproject.trade.domain.dto.PostResponseDto;
import com.hanghaecloneproject.trade.domain.dto.PostsResponseDto;
import com.hanghaecloneproject.trade.domain.service.TradeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController

public class TradeReadController {

    private final TradeReadService tradeReadService;

    //중고거래 상세페이지 조회
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<PostResponseDto> readOne(
            @PathVariable Long postId) {

        return ResponseEntity.status(HttpStatus.OK)
                .header("status","200")
                .body(tradeReadService.getReadOne(postId));
}

    //중고거래 전체 리스트 조회
    @GetMapping("api/posts")
    public ResponseEntity<Slice<PostsResponseDto>> readAll(
            @RequestParam String keyword,
            @RequestParam String location,
            @RequestParam int size,
            @RequestParam Long lastId) {

        return ResponseEntity.status(HttpStatus.OK)
                .header("status","200")
                .body(tradeReadService.getReadAll(keyword,location,size, lastId));

    }
}
