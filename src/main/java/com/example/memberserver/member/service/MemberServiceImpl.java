package com.example.memberserver.member.service;


import com.example.memberserver.member.dto.request.RequestMemberLoginDto;
import com.example.memberserver.member.dto.request.RequestMemberPointDto;
import com.example.memberserver.member.dto.request.RequestOrderDto;
import com.example.memberserver.member.dto.response.ResponseMessageDto;
import com.example.memberserver.member.dto.response.ResponsePointDto;
import com.example.memberserver.member.dto.response.ResponseTokenDto;
import com.example.memberserver.member.entity.Member;
import com.example.memberserver.member.exception.PasswordMismatchException;
import com.example.memberserver.member.exception.UserNotFoundException;
import com.example.memberserver.member.jwt.JwtGenerator;
import com.example.memberserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public ResponsePointDto getPoint(Long memberId) {
        Member member = findByMemberId(memberId);

        return new ResponsePointDto(member);
    }

    @Override
    public ResponseMessageDto addPoint(RequestMemberPointDto requestDto, Long memberId) {
        Member member = findByMemberId(memberId);
        member.rechargePoint(requestDto.getPoint());
        return new ResponseMessageDto("충전 완료", HttpStatus.OK.value(), HttpStatus.OK.toString());
    }

    @Transactional
    @Override
    public void updateDeposit(Long memberId, Long bid) {
        Member member = findByMemberId(memberId);
        if (member.getPoint() < bid) {
            throw new IllegalStateException("예치금은 포인트보다 클 수 없습니다.");
        }
        member.setDeposit(bid);
    }
    @Transactional

    @Override
    public void subtractPointsOnBidSuccess(Long memberId, Long bid) {
        Member member = findByMemberId(memberId);
        log.info("bid = {}, deposit ={}", bid, member.getDeposit());

        if (!member.getDeposit().equals(bid)) {
            log.error("입찰금액이 해당 회원의 예치금과 다릅니다.");
            throw new IllegalArgumentException("입찰금액이 해당 회원의 예치금과 다릅니다.");
        }
        member.subtractPointsOnBidSuccess();
    }
    @Transactional
    @Override
    public void payPoint(Long price, Long memberId) {
        Member member = findByMemberId(memberId);

        if (member.getAvailablePoint() < price) {
            throw new IllegalArgumentException("결제 금액은 가용 포인트보다 클 수 없습니다.");
        }
        member.payPoint(price);
    }

    private Member findByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
    }
}
