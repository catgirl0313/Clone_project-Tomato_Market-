package com.hanghaecloneproject.trade.service;

import com.hanghaecloneproject.trade.domain.Trade;
import com.hanghaecloneproject.trade.domain.TradeImage;
import com.hanghaecloneproject.trade.dto.PostResponseDto;
import com.hanghaecloneproject.trade.dto.PostsResponseDto;
import com.hanghaecloneproject.trade.repository.TradeReadRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Service
@Builder
public class TradeReadService {

    private final TradeReadRepository tradeReadRepository;

    //상세 게시글 조회
    public PostResponseDto getReadOne(Long postId) {
        Trade trade = tradeReadRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("게시글이 없습니다."));

        PostResponseDto postResponseDto = PostResponseDto.builder()
                        .postId(trade.getId())
                        .category(trade.getCategory())
                        .content(trade.getContent())
                        .createdAt(trade.getCreatedAt())
                        .image(trade.getTradeImages().stream().map(TradeImage::getImageUrl).collect(Collectors.toList()))
                        .seller(null)
                        .title(trade.getTitle())
                        .like(trade.getLike())
                        .price(trade.getPrice())
                        .view(trade.getView())
                        .status(trade.getStatus())
                        .build();

        return postResponseDto;
    }

    //전체 게시물 조회
    public Slice<PostsResponseDto> getReadAll(String keyword, String location, int size, Long lastId) {
        Pageable pageable = PageRequest.ofSize(size);
        Slice<Trade> tradeSlice = tradeReadRepository.findByIdGreaterThanAndTitleContainingAndAddressOrderByIdDesc(lastId , keyword, location, pageable);
        Slice<PostsResponseDto> postsResponseDtoSlice = postTopostsResponseDtos(tradeSlice);
        return postsResponseDtoSlice;
    }

    private Slice<PostsResponseDto> postTopostsResponseDtos(Slice<Trade> postSlice) {
        Slice<PostsResponseDto> postsResponseDtoSlice = postSlice.map(p->
                PostsResponseDto.builder()
                        .address(p.getAddress())
                        .postId(p.getId())
                        .category(p.getCategory())
                        .image(p.getTradeImages().stream().map(TradeImage::getImageUrl).collect(Collectors.toList()).get(0))
                        .createdAt(p.getCreatedAt())
                        .price(p.getPrice())
                        .like(p.getLike())
                        .view(p.getView())
                        .status(p.getStatus())
                        .build()
        );
        return postsResponseDtoSlice;
    }
}
