package com.example.memberserver.member.service;


import com.example.memberserver.member.dto.request.RequestMemberLoginDto;
import com.example.memberserver.member.dto.response.ResponseTokenDto;

public interface MemberService {
    ResponseTokenDto login(RequestMemberLoginDto requestDto);
}
