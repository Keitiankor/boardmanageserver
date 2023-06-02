package com.keitian.boardmanageserver.account.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keitian.boardmanageserver.account.dto.AccountDTO;
import com.keitian.boardmanageserver.account.dto.TokenDTO;
import com.keitian.boardmanageserver.global.jwt.JwtFilter;
import com.keitian.boardmanageserver.global.jwt.TokenProvider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class AccountController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AccountDTO accountDTO) {

        // dto를 통해 가져온 정보로 인증토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                accountDTO.getAccount(), accountDTO.getPassword());

        // 인증토큰으로 인증데이터 생성
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        // 토큰프로바이더를 통해 인증 문자열 생성
        String jwt = tokenProvider.creationToken(authentication);

        // 검증 프리픽스를 보함시켜 리스폰스 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Keitian " + jwt);

        // 리스폰스
        return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
    }
}
