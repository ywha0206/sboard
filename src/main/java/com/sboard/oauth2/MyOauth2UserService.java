package com.sboard.oauth2;

import com.sboard.entity.User;
import com.sboard.repository.UserRepository;
import com.sboard.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class MyOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("Load user... 1 : "+userRequest);

        String accessToken = userRequest.getAccessToken().getTokenValue();
        log.info("Load user... 2 : "+accessToken);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("Load user... 3 : "+provider);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("Load user... 4 : "+oAuth2User);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Load user... 5 : "+attributes);


        //사용자 확인 및 회원가입 처리
        String email = (String) attributes.get("email");
        String uid = email.split("@")[0];
        String name = attributes.get("given_name").toString();
        String nick = attributes.get("name").toString();

        User user = userRepository.findById(uid).orElse(null);

        if (user != null) {
            //회원이 존재하면 시큐리티 인증 처리(로그인)
            return MyUserDetails
                    .builder()
                    .user(user)
                    .accessToken(accessToken)
                    .attributes(attributes)
                    .build();
        }else{
            //회원이 존재하지 않으면 회원가입 처리
            user = User.builder()
                    .uid(uid)
                    .nick(nick)
                    .email(email)
                    .name(name)
                    .build();

            userRepository.save(user);

            return MyUserDetails
                    .builder()
                    .user(user)
                    .accessToken(accessToken)
                    .attributes(attributes)
                    .build();
        }

    }
}
