package com.example.memberserver.member.controller;


import com.example.memberserver.member.dto.request.RequestMemberLoginDto;
import com.example.memberserver.member.dto.response.ResponseDataDto;
import com.example.memberserver.member.dto.response.ResponseMessageDto;
import com.example.memberserver.member.dto.response.ResponseTokenDto;
import com.example.memberserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDataDto> login(@RequestBody RequestMemberLoginDto requestDto) {
        ResponseTokenDto loginResponse = memberService.login(requestDto);
        ResponseMessageDto responseMessage = new ResponseMessageDto("로그인 완료", HttpStatus.OK.value(), HttpStatus.OK.toString());
        ResponseDataDto<ResponseMessageDto> response = new ResponseDataDto<>(responseMessage);
        return ResponseEntity.status(HttpStatus.OK)
                .header(loginResponse.getHeaderName(), loginResponse.getAccessTokenValue())
                .body(response);
    }
}
