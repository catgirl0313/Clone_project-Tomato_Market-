package com.hanghaecloneproject.trade.service;

import com.hanghaecloneproject.common.Image.domain.ImageType;
import com.hanghaecloneproject.common.Image.repository.ImageRepository;
import com.hanghaecloneproject.common.s3.S3Service;
import com.hanghaecloneproject.trade.domain.Trade;
import com.hanghaecloneproject.trade.domain.TradeImage;
import com.hanghaecloneproject.trade.dto.PostCUDResponseDto;
import com.hanghaecloneproject.trade.dto.PostRequestDto;
import com.hanghaecloneproject.trade.repository.PostRepository;
import com.hanghaecloneproject.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Component
@RequiredArgsConstructor
public class TradeService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @Transactional
    public PostCUDResponseDto createPost(PostRequestDto postRequestDto, List<MultipartFile> files,
          User user) {
        validateTradeDto(postRequestDto);

        //post 저장
        Trade tradeEntity = postRequestDto.toEntity();
        tradeEntity.settingUserInfo(user.getAddress(), user.getId());

        //이미지 URL 저장하기
        List<String> savedUrls = s3Service.uploadFileInS3(files, "trade/");
        List<TradeImage> tradeImages = savedUrls
              .stream()
              .map(TradeImage::new)
              .collect(Collectors.toList());

        tradeEntity.setTradeImages(tradeImages);

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
        if (dto.getPrice() > 0) {
            throw new IllegalArgumentException("0원 이상의 가격을 넣어주세요.");
        }
    }

    private void validateContent(PostRequestDto dto) {
        if (dto.getContent().isBlank()) {
            throw new IllegalArgumentException("내용을 넣어주세요.");
        }
    }

    //게시글 수정
    public void updatePost(Long postId, PostRequestDto postDto, List<MultipartFile> files,
          User user) {
        //item
        Trade trade = postRepository.findById(postId)
              .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));

        validateUser(user, trade);
        validateTradeDto(postDto);

        // 사진 갈아끼우기
        if (files != null) {
            List<String> deleteUrls = trade.getTradeImages().stream()
                  .map(TradeImage::getImageUrl)
                  .collect(Collectors.toList());
            List<String> imagePaths = s3Service.update(deleteUrls, files, "trade/");
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
        imageRepository.deleteByImageTypeAndObjectId(ImageType.TRADE_IMAGE, postId);
        postRepository.deleteById(postId);
        //System.out.println("삭제확인");

        return postId;
    }

}
