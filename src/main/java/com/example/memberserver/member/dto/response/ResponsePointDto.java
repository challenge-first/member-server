package com.example.memberserver.member.dto.response;

import com.example.memberserver.member.entity.Member;
import lombok.Getter;

@Getter
public class ResponsePointDto {

    private Long point;
    private Long deposit;
    private Long availablePoint;

    public ResponsePointDto(Member member) {
        this.point = member.getPoint();
        this.deposit = member.getDeposit();
        this.availablePoint = member.getAvailablePoint();
    }
}
