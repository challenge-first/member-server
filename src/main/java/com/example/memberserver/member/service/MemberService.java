package com.example.memberserver.member.service;


import com.example.memberserver.member.dto.request.RequestMemberLoginDto;
import com.example.memberserver.member.dto.request.RequestMemberPointDto;
import com.example.memberserver.member.dto.response.ResponseMessageDto;
import com.example.memberserver.member.dto.response.ResponsePointDto;
import com.example.memberserver.member.dto.response.ResponseTokenDto;

public interface MemberService {
    ResponseTokenDto login(RequestMemberLoginDto requestDto);

    ResponsePointDto getPoint(Long memberId);

    ResponseMessageDto addPoint(RequestMemberPointDto requestDto, Long memberId);

    void updateDeposit(Long memberId, Long bid);

    void subtractPointsOnBidSuccess(Long memberId, Long bid);

    void payPoint(Long memberId, Long price);
}
