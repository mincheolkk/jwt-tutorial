package me.prac.tutorial.controller;

import me.prac.tutorial.dto.LoginDto;
import me.prac.tutorial.dto.TokenDto;
import me.prac.tutorial.jwt.JwtFilter;
import me.prac.tutorial.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    // TokenProvider , AuthenticationManagerBuilder 를 주입 받음.

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // LoginDto 의 username, password 를 파라미터로 받고 이를 이용해 UsernamePasswordAuthenticationToken 을 생성함.

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 생성된 authenticationToken 을 이용해서 Authentication 객체를 생성하는데 ,
        // authenticate 메소드가 실행될 때, CustomUserDetailsService 에서 loadUserByUsername 메소드가 실행되고 이 결과값을 가지고,
        // Authentication 객체를 생성하고 이를 SecurityContext 에 저장.

        String jwt = tokenProvider.createToken(authentication);

        // authentication 인증정보를 기준으로 해서 createToken 메소드를 통해서 JWT Token 을 생성함.

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // JWT Token 을 Response Header 에 넣어줌

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);

        // JWT Token 을 TokenDto 를 이용해서 Response Body 에도 넣어서 리턴해줌 .
    }
}