package com.hanghaecloneproject.trade.controller;

import com.hanghaecloneproject.trade.dto.PostResponseDto;
import com.hanghaecloneproject.trade.dto.PostsResponseDto;
import com.hanghaecloneproject.trade.service.TradeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
              .body(tradeReadService.getReadOne(postId));
    }

    //중고거래 전체 리스트 조회
    @GetMapping("api/posts")
    public ResponseEntity<Slice<PostsResponseDto>> readAll(
        @RequestParam(defaultValue = "") String keyword,
        @RequestParam(defaultValue = "") String address,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 8) Pageable pageable,
        @RequestParam(defaultValue = "" + Long.MAX_VALUE) Long lastId) {

        return ResponseEntity.status(HttpStatus.OK)
              .body(tradeReadService.getReadAll(keyword, address, pageable, lastId));

    }
}
