package com.example.memberserver.member.controller;


import com.example.memberserver.auth.auth.LoginMember;
import com.example.memberserver.member.dto.request.RequestMemberLoginDto;
import com.example.memberserver.member.dto.request.RequestMemberPointDto;
import com.example.memberserver.member.dto.response.ResponseDataDto;
import com.example.memberserver.member.dto.response.ResponseMessageDto;
import com.example.memberserver.member.dto.response.ResponsePointDto;
import com.example.memberserver.member.dto.response.ResponseTokenDto;
import com.example.memberserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // members/memberId
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponsePointDto> getPoint(@PathVariable Long memberId) {
        ResponsePointDto response = memberService.getPoint(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/point")
    public ResponseEntity<ResponseDataDto<ResponseMessageDto>> addPoint(@RequestBody RequestMemberPointDto requestDto, @LoginMember Long memberId) {
        ResponseDataDto<ResponseMessageDto> response = new ResponseDataDto<>(memberService.addPoint(requestDto, memberId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
