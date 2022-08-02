package com.hanghaecloneproject.trade.service;

import com.hanghaecloneproject.common.Image.repository.ImageRepository;
import com.hanghaecloneproject.trade.domain.Trade;
import com.hanghaecloneproject.trade.domain.TradeImage;
import com.hanghaecloneproject.trade.dto.PostResponseDto;
import com.hanghaecloneproject.trade.dto.PostsResponseDto;
import com.hanghaecloneproject.trade.dto.SellerDto;
import com.hanghaecloneproject.trade.repository.TradeReadRepository;
import com.hanghaecloneproject.user.domain.User;
import com.hanghaecloneproject.user.repository.UserRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TradeReadService {

    private final TradeReadRepository tradeReadRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    //상세 게시글 조회
    public PostResponseDto getReadOne(Long postId) {
        Trade trade = tradeReadRepository.findById(postId)
              .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        User user = userRepository.findById(trade.getUserId())
              .orElseThrow(() -> new IllegalArgumentException("없는 회원정보입니다."));

        return PostResponseDto.builder()
              .postId(trade.getId())
              .category(trade.getCategory())
              .content(trade.getContent())
              .createdAt(trade.getCreatedAt())
              .image(trade.getTradeImages().stream().map(TradeImage::getImageUrl)
                    .collect(Collectors.toList()))
              .seller(new SellerDto(user.getNickname(), user.getProfileImage(),
                    user.getAddress()))
              .title(trade.getTitle())
              .like(trade.getLike())
              .price(trade.getPrice())
              .view(trade.getView())
              .status(trade.getStatus())
              .build();
    }

    //전체 게시물 조회
    public Slice<PostsResponseDto> getReadAll(String keyword, String location, int size,
          Long lastId) {
        Pageable pageable = PageRequest.ofSize(size);
        Slice<Trade> tradeSlice = tradeReadRepository.findByIdGreaterThanAndTitleContainingAndAddressOrderByIdDesc(
              lastId, keyword, location, pageable);
        return postToPostsResponseDtos(tradeSlice);
    }

    private Slice<PostsResponseDto> postToPostsResponseDtos(Slice<Trade> postSlice) {
        return postSlice.map(p ->
              PostsResponseDto.builder()
                    .address(p.getAddress())
                    .postId(p.getId())
                    .category(p.getCategory())
                    .image(p.getTradeImages().stream()
                          .filter(TradeImage::isRepresentative)
                          .map(TradeImage::getImageUrl)
                          .findFirst().get())
                    .createdAt(p.getCreatedAt())
                    .price(p.getPrice())
                    .like(p.getLike())
                    .view(p.getView())
                    .status(p.getStatus())
                    .build()
        );
    }

}
