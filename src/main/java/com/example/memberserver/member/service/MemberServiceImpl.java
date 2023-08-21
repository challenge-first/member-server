package com.example.memberserver.member.service;


import com.example.memberserver.member.dto.request.RequestMemberLoginDto;
import com.example.memberserver.member.dto.response.ResponseTokenDto;
import com.example.memberserver.member.entity.Member;
import com.example.memberserver.member.exception.PasswordMismatchException;
import com.example.memberserver.member.exception.UserNotFoundException;
import com.example.memberserver.member.jwt.JwtGenerator;
import com.example.memberserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtGenerator jwtGenerator;

    @Override
    public ResponseTokenDto login(RequestMemberLoginDto requestDto) {
        Member member = memberRepository.findByUsername(requestDto.getUsername())
            .orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));

        if (!requestDto.getPassword().equals(member.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtGenerator.createAccessToken(member.getId(),member.getRole());
        return new ResponseTokenDto(JwtGenerator.HEADER_NAME, accessToken);
    }

}
