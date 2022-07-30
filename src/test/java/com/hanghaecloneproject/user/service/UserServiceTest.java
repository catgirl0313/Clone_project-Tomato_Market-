package com.hanghaecloneproject.user.service;

import com.hanghaecloneproject.user.dto.SignupRequestDto;
import com.hanghaecloneproject.user.dto.SignupRequestDto.AddressDto;
import com.hanghaecloneproject.user.exception.BadPasswordPatternException;
import com.hanghaecloneproject.user.exception.DuplicateNicknameException;
import com.hanghaecloneproject.user.exception.DuplicateUsernameException;
import com.hanghaecloneproject.user.exception.MismatchedPasswordException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class UserServiceTest {

    private static Validator validator;

    @Autowired
    private UserService userService;

    @DisplayName(value = "회원 아이디 중복이 되어서는 안된다.")
    @Test
    void usernameDuplicationTest() {
        AddressDto addressDto = new AddressDto();
        addressDto.setCircuit("경상북도");
        SignupRequestDto dto1 = new SignupRequestDto("username@gmail.com", "password1!",
              "password1!", "user1", null, addressDto);
        SignupRequestDto dto2 = new SignupRequestDto("username@gmail.com", "password1!",
              "password1!", "user2", null, addressDto);

        Assertions.assertThatCode(() -> userService.signup(dto1)).doesNotThrowAnyException();
        Assertions.assertThatThrownBy(() -> userService.signup(dto2))
              .isInstanceOf(DuplicateUsernameException.class)
              .hasMessageContaining("이미 존재하는 아이디");
    }

    @DisplayName(value = "아이디가 이메일 형식을 지키는가?.")
    @Test
    void isUsernameEmailFormTest() {
        AddressDto addressDto = new AddressDto();
        addressDto.setCircuit("경상북도");
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<SignupRequestDto>> result1 = validator.validate(
              new SignupRequestDto("username1@gmail.com", "password1!", "password1!", "user1",
                    null, addressDto));
        Set<ConstraintViolation<SignupRequestDto>> result2 = validator.validate(
              new SignupRequestDto("username2", "password1!", "password1!", "user2", null,
                    addressDto));

        Assertions.assertThat(result1).isEmpty();
        Assertions.assertThat(result2).isNotEmpty();
    }

    @DisplayName(value = "비밀번호와 비밀번호 재입력은 일치해야 한다.")
    @Test
    void passwordEqualsTest() {
        AddressDto addressDto = new AddressDto();
        addressDto.setCircuit("경상북도");
        SignupRequestDto dto1 = new SignupRequestDto("username@gmail.com", "password1!",
              "password1!", "user1", null, addressDto);
        SignupRequestDto dto2 = new SignupRequestDto("username1@gmail.com", "password2!",
              "password1!", "user2", null, addressDto);

        Assertions.assertThatCode(() -> userService.signup(dto1)).doesNotThrowAnyException();
        Assertions.assertThatThrownBy(() -> userService.signup(dto2))
              .isInstanceOf(MismatchedPasswordException.class)
              .hasMessageContaining("일치");
    }

    @DisplayName(value = "비밀번호는 최소 8자리에 숫자, 문자, 특수문자를 각각 1개 이상 포함해야 한다")
    @Test
    void passwordRegexTest() {
        AddressDto addressDto = new AddressDto();
        addressDto.setCircuit("경상북도");
        SignupRequestDto dto1 = new SignupRequestDto("username1@gmail.com", "password1!",
              "password1!", "user1", null, addressDto);
        SignupRequestDto dto2 = new SignupRequestDto("username2@gmail.com", "pw", "pw",
              "user2", null, addressDto);

        Assertions.assertThatNoException().isThrownBy(() -> userService.signup(dto1));
        Assertions.assertThatThrownBy(() -> userService.signup(dto2))
              .isInstanceOf(BadPasswordPatternException.class)
              .hasMessageContaining("8자리에 숫자, 문자, 특수문자");
    }

    @DisplayName(value = "닉네임은 중복되어서는 안된다.")
    @Test
    void duplicateNicknameTest() {
        AddressDto addressDto = new AddressDto();
        addressDto.setCircuit("경상북도");
        SignupRequestDto dto1 = new SignupRequestDto("username1", "password1!", "password1!",
              "user1", null, addressDto);
        SignupRequestDto dto2 = new SignupRequestDto("username2", "password1!", "password1!",
              "user1", null, addressDto);

        Assertions.assertThatNoException().isThrownBy(() -> userService.signup(dto1));
        Assertions.assertThatThrownBy(() -> userService.signup(dto2))
              .isInstanceOf(DuplicateNicknameException.class)
              .hasMessageContaining("이미 존재하는 닉네임");

    }
}
