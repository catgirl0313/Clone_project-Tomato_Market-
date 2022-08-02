package com.hanghaecloneproject.post.service;

import com.hanghaecloneproject.User;
import com.hanghaecloneproject.post.domain.Category;
import com.hanghaecloneproject.post.domain.ImageUrl;
import com.hanghaecloneproject.post.domain.Post;
import com.hanghaecloneproject.post.dto.PostResponseDto;
import com.hanghaecloneproject.post.dto.PostRequestDto;
import com.hanghaecloneproject.post.repository.CategoryRepository;
import com.hanghaecloneproject.post.repository.ImageUrlRepository;
import com.hanghaecloneproject.post.repository.PostRepository;

import com.hanghaecloneproject.post.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageUrlRepository imageUrlRepository;
    private final S3Service s3Service;
    private final CategoryRepository categoryRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, List<MultipartFile> files, User user){
        String title = postRequestDto.getTitle();
        int price = postRequestDto.getPrice();
        String content = postRequestDto.getContent();
        Long categoryId = postRequestDto.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                //유효성 검사
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다!"));
         if (title == null) {
            throw new IllegalArgumentException("내용을 적어주세요.");
        } else if (price < 0) {
            throw new IllegalArgumentException("0원 이상의 가격을 넣어주세요.");
        } else if (content == null) {
            throw new IllegalArgumentException("내용을 넣어주세요.");
        }

        //post 저장
        Post post = new Post(user, title, price, content, category);
        postRepository.save(post);

        //이미지 URL 저장하기
        List<String> imagePaths = s3Service.upload(files);
//        System.out.println("최초 게시하는 Image경로들 모아놓은것 :"+ imagePaths);

        List<String> images = new ArrayList<>(); //return 값 확인
        for(String imageUrl : imagePaths){
            ImageUrl image = new ImageUrl(imageUrl, post);
            imageUrlRepository.save(image);
            post.addImage(image);
        }
        //return 값 생성
        return new PostResponseDto(title, price, category, images);

    }

    //게시글 수정
    public PostResponseDto updatePost(Long postId, PostRequestDto postDto, List<MultipartFile> files, User user) {
        //item
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));
        //작성자 검사
        Long postUserId = post.getUser().getId();
        System.out.println("postUserId = " + postUserId);
        if (!user.getId().equals(postUserId)){
            throw new IllegalArgumentException("작성자가 아니므로, 해당 게시글을 수정할 수 없습니다.");
        }
        //카테고리
        Category category = categoryRepository.findById(postDto.getCategory().getId())

        //유효성 검사
                .orElseThrow(() -> new IllegalStateException("해당 카테고리가 없습니다."));
        if (postDto.getTitle() == null) {
            throw new IllegalArgumentException("내용을 적어주세요.");
        } else if (postDto.getPrice() < 0) {
            throw new IllegalArgumentException("0원 이상의 가격을 넣어주세요.");
        } else if (postDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 넣어주세요.");
        }

        List<String> imagePaths = s3Service.update(postId, files);
//        System.out.println("수정된 Image경로들 모아놓은것 :"+ imagePaths);

        post.update(postDto, category);

        //이미지 URL 저장하기
        List<String> images = new ArrayList<>(); //return 값 확인
        for(String imageUrl : imagePaths){
            ImageUrl image = new ImageUrl(imageUrl, post);
            imageUrlRepository.save(image);
            images.add(image.getImageUrls());
        }
        return new PostResponseDto(post, images);
    }

    //게시글 삭제
    public Long deletePost(Long postId, User user) {
        //유효성 검사
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));
        //작성자 검사
        Long itemUserId = post.getUser().getId();
        if (!user.getId().equals(itemUserId)){
            throw new IllegalArgumentException("작성자가 아니므로, 해당 게시글을 삭제할 수 없습니다.");
        }
        //S3 사진, ImageURl, item 삭제
        s3Service.delete(postId);
        imageUrlRepository.deleteAllByPostId(postId);
        postRepository.deleteById(postId);
        //System.out.println("삭제확인");

        return postId;
    }

}
