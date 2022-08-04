package com.hanghaecloneproject.trade.service;

import com.hanghaecloneproject.common.s3.S3Service;
import com.hanghaecloneproject.trade.domain.Trade;
import com.hanghaecloneproject.trade.domain.TradeImage;
import com.hanghaecloneproject.trade.dto.PostCUDResponseDto;
import com.hanghaecloneproject.trade.dto.PostRequestDto;
import com.hanghaecloneproject.trade.repository.PostRepository;
import com.hanghaecloneproject.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeService {

    private final PostRepository postRepository;
    private final S3Service s3Service;

    @Transactional
    public PostCUDResponseDto createPost(PostRequestDto postRequestDto, User user) {
        validateTradeDto(postRequestDto);

        //post 저장
        Trade tradeEntity = postRequestDto.toEntity();
        tradeEntity.settingUserInfo(user.getAddress(), user.getId());

        //이미지 URL 저장하기
        List<String> savedUrls = s3Service.uploadFileInS3(postRequestDto.getImage(), "trade/");
        List<TradeImage> tradeImages = savedUrls
              .stream()
              .map(TradeImage::new)
              .collect(Collectors.toList());

        log.info("insert into s3 complete! -> {}", tradeImages);

        tradeEntity.addTradeImages(tradeImages);

        log.info("entity -> {}", tradeEntity);

        postRepository.save(tradeEntity);

        //return 값 생성
        return new PostCUDResponseDto(tradeEntity, user, savedUrls);
    }

    private void validateTradeDto(PostRequestDto postRequestDto) {
        validateTitle(postRequestDto);
        validateContent(postRequestDto);
        validatePrice(postRequestDto);
    }

    private void validateTitle(PostRequestDto dto) {
        if (dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목을 적어주세요.");
        }
    }

    private void validatePrice(PostRequestDto dto) {
        if (dto.getPrice() <= 0) {
            throw new IllegalArgumentException("0원 이상의 가격을 넣어주세요.");
        }
    }

    private void validateContent(PostRequestDto dto) {
        if (dto.getContent().isBlank()) {
            throw new IllegalArgumentException("내용을 넣어주세요.");
        }
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, PostRequestDto postDto, User user) {
        //item
        Trade trade = postRepository.findById(postId)
              .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));

        validateUser(user, trade);
        validateTradeDto(postDto);

        // 사진 갈아끼우기
        if (postDto.getImage() != null) {
            List<String> deleteUrls = trade.getTradeImages().stream()
                  .map(TradeImage::getImageUrl)
                  .collect(Collectors.toList());
            List<String> imagePaths = s3Service.update(deleteUrls, postDto.getImage(), "trade/");
        }

        // TODO 업데이트 로직 짜야 함.
        trade.update(postDto);
    }

    private static void validateUser(User user, Trade trade) {
        Long postUserId = trade.getUserId();
        if (!user.getId().equals(postUserId)) {
            throw new IllegalArgumentException("작성자가 아니므로, 해당 게시글을 수정할 수 없습니다.");
        }
    }

    //게시글 삭제
    @Transactional
    public Long deletePost(Long postId, User user) {
        //유효성 검사
        Trade trade = postRepository.findById(postId)
              .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));

        //작성자 검사
        validateUser(user, trade);

        List<String> imageUrls = trade.getTradeImages().stream()
              .map(TradeImage::getImageUrl)
              .collect(Collectors.toList());

        //S3 사진, ImageURl, item 삭제
        s3Service.deleteFileInS3(imageUrls);
        postRepository.deleteById(postId);
        //System.out.println("삭제확인");

        return postId;
    }

}
