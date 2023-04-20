package com.food.withkitchen.service.impl;

import com.food.withkitchen.dto.UserLoginDTO;
import com.food.withkitchen.dto.UserSignUpDTO;
import com.food.withkitchen.entity.User;
import com.food.withkitchen.enums.Role;
import com.food.withkitchen.repository.UserRepository;
import com.food.withkitchen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void DuplicateFields(UserSignUpDTO userSignUpDTO, BindingResult bindingResult) {
        logger.info("[signUp] 중복 검사)");
        if (userRepository.existsByUsername(userSignUpDTO.getUsername()))
            bindingResult.rejectValue("username", "Duplicate", "이미 사용 중인 아이디입니다.");
        if (userRepository.existsByNickname(userSignUpDTO.getNickname()))
            bindingResult.rejectValue("nickname", "Duplicate", "이미 사용 중인 닉네임입니다.");
        if (userRepository.existsByEmail(userSignUpDTO.getEmail()))
            bindingResult.rejectValue("email", "Duplicate", "이미 사용 중인 이메일입니다.");
    }
    @Override
    public void signUp(UserSignUpDTO userSignUpDTO) {
        logger.info("[signUp] 회원 가입 정보 전달됨");
        User user;

        if (userSignUpDTO.getRole().equalsIgnoreCase("ADMIN")) {
            user = User.builder()
                    .username(userSignUpDTO.getUsername())
                    .password(userSignUpDTO.getPassword())
                    .email(userSignUpDTO.getEmail())
                    .nickname(userSignUpDTO.getNickname())
                    .role(Role.ADMIN)
                    .build();
        } else {
            user = User.builder()
                    .username(userSignUpDTO.getUsername())
                    .password(userSignUpDTO.getPassword())
                    .email(userSignUpDTO.getEmail())
                    .nickname(userSignUpDTO.getNickname())
                    .role(Role.USER)
                    .build();
        }

       userRepository.save(user);
    }


    @Override
    public void MatchPassword(UserLoginDTO userLoginDTO, BindingResult bindingResult) {
        logger.info("[login] 패스워드 비교 수행)");

        Optional<User> user = userRepository.findByUsername(userLoginDTO.getUsername());
        if (user.isEmpty()) bindingResult.rejectValue("password", "Mismatch", "아이디나 비밀번호를 확인해주세요.");
        else if (!passwordEncoder.matches(userLoginDTO.getPassword(), userRepository.getByUsername(userLoginDTO.getUsername()).getPassword())) {
            logger.info("[login] 패스워드 불일치");
            bindingResult.rejectValue("password", "Mismatch", "아이디나 비밀번호를 확인해주세요.");
        }
    }

    @Override
    public void login(UserLoginDTO userLoginDTO) {
        logger.info("[login] 로그인 정보 전달됨");
        User user = userRepository.getByUsername(userLoginDTO.getUsername());

        logger.info("[login] Id: {}", userLoginDTO.getUsername());
    }
}
