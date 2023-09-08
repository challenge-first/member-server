package com.example.memberserver.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class RequestMemberPointDto {

    private Long point;

    public RequestMemberPointDto(Long point) {
        this.point = point;
    }
}
