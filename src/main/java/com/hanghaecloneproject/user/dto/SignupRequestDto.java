package com.hanghaecloneproject.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanghaecloneproject.user.domain.User;
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
public class SignupRequestDto implements Serializable {

    @Email private String username;
    @NotBlank private String password;
    @NotBlank private String passwordCheck;
    @NotBlank private String nickname;
    private MultipartFile profileImage;

    @JsonProperty(value = "address")
    private AddressDto addressDto;

    public User toEntity() {
        return new User(username, password, nickname, "시군구");
    }

    @Setter
    @Getter
    public static class AddressDto implements Serializable {

        private String circuit;
        private String si;
        private String gu;
        private String dong;

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
