package com.hanghaecloneproject.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanghaecloneproject.user.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Setter
@Getter
@NoArgsConstructor
@ApiModel(value = "회원가입 폼")
public class SignupRequestDto implements Serializable {

    @Email
    @ApiModelProperty(value = "아이디(이메일 형식)", required = true)
    private String username;

    @NotBlank
    @ApiModelProperty(value = "패스워드", required = true)
    private String password;

    @NotBlank
    @ApiModelProperty(value = "패스워드 재확인", required = true)
    private String passwordCheck;

    @NotBlank
    @ApiModelProperty(value = "닉네임", required = true)
    private String nickname;

    @ApiModelProperty(value = "프로필 사진", required = true)
    private MultipartFile profileImage;

    @JsonProperty(value = "address")
    @ApiModelProperty(value = "주소", required = true)
    private AddressDto addressDto;

    public User toEntity() {
        return new User(username, password, nickname, "시군구");
    }

    @ApiModel(value = "주소 정보를 가지는 클래스")
    @Setter
    @Getter
    public static class AddressDto implements Serializable {

        @ApiModelProperty(value = "도", required = true) private String circuit;
        @ApiModelProperty(value = "시", required = true) private String si;
        @ApiModelProperty(value = "구", required = false) private String gu;
        @ApiModelProperty(value = "동, 면, 읍, 등", required = true) private String dong;

        @Override
        public String toString() {
            return circuit + " " + si + " " + gu + " " + dong;
        }
    }

    public SignupRequestDto(String username, String password, String passwordCheck, String nickname,
          MultipartFile profileImage, AddressDto addressDto) {
        this.username = username;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.addressDto = addressDto;
    }
}
