package com.example.memberserver.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class RequestMemberPointDto {

    private Long point;

    public RequestMemberPointDto(Long point) {
        this.point = point;
    }
}
