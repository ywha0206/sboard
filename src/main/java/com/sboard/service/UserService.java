package com.sboard.service;

import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import com.sboard.entity.Terms;
import com.sboard.entity.User;
import com.sboard.repository.TermsRepository;
import com.sboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;
    private final PasswordEncoder passwordEncoder;

    public void insertUser(UserDTO userDTO) {
        //회원가입
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        User user = userDTO.toEntity();
        userRepository.save(user);
    }

    public UserDTO selectUser(String uid) {

        Optional<User> optUser = userRepository.findById(uid);

        if(optUser.isPresent()) {
            User user = optUser.get();
            return user.toDTO();
        }
        return null;
    }

    public TermsDTO selectTerms() {
    Terms terms = termsRepository.findterms();


        TermsDTO termsDTO = terms.toDTO();

    return termsDTO;

    }

    public boolean checkUserIdExists(String uid){
        return userRepository.existsById(uid);
    }


}
