package com.hanghaecloneproject.user.service;

import com.hanghaecloneproject.common.s3.S3Service;
import com.hanghaecloneproject.config.security.dto.UserDetailsImpl;
import com.hanghaecloneproject.user.domain.User;
import com.hanghaecloneproject.user.dto.CheckNicknameDto;
import com.hanghaecloneproject.user.dto.CheckUsernameDto;
import com.hanghaecloneproject.user.dto.SignupRequestDto;
import com.hanghaecloneproject.user.exception.BadPasswordPatternException;
import com.hanghaecloneproject.user.exception.DuplicateNicknameException;
import com.hanghaecloneproject.user.exception.DuplicateUsernameException;
import com.hanghaecloneproject.user.exception.MismatchedPasswordException;
import com.hanghaecloneproject.user.repository.UserRepository;
import java.util.regex.Pattern;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
          "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$");

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository,
          S3Service s3Service) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.s3Service = s3Service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
              .map(UserDetailsImpl::new)
              .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));
    }

    public void signup(SignupRequestDto dto) {
        validateSignupInfo(dto);

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        User user = dto.toEntity();

        if (dto.getProfileImage() != null) {
            String savedUrl = s3Service.uploadOne(dto.getProfileImage(), "profile/");
            user.setProfileImage(savedUrl);
        }

        userRepository.save(user);
    }

    private void validateSignupInfo(SignupRequestDto dto) {
        checkDuplicateUsername(dto.getUsername());
        checkDuplicateNickname(dto.getNickname());
        checkMatchPassword(dto);
        checkPasswordPattern(dto);
    }

    private void checkMatchPassword(SignupRequestDto dto) {
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new MismatchedPasswordException("비밀번호와 비밀번호 재확인이 일치하지 않습니다. 다시 확인해주세요");
        }
    }

    private void checkPasswordPattern(SignupRequestDto dto) {
        if (!PASSWORD_PATTERN.matcher(dto.getPassword()).matches()) {
            throw new BadPasswordPatternException(
                  ("비밀번호는 최소 8자리에 숫자, 문자, 특수문자를 각각 1개 이상 포함해야 합니다."));
        }
    }

    public void checkDuplicateUsername(CheckUsernameDto dto) {
        checkDuplicateUsername(dto.getUsername());
    }

    private void checkDuplicateUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicateUsernameException("이미 존재하는 아이디입니다. 입력한 아이디: " + username);
        }
    }

    public void checkDuplicateNickname(CheckNicknameDto dto) {
        checkDuplicateNickname(dto.getNickname());
    }

    private void checkDuplicateNickname(String nickname) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateNicknameException("이미 존재하는 닉네임입니다. 입력한 닉네임: " + nickname);
        }
    }


}
