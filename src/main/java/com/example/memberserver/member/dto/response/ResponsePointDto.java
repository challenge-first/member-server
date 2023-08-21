package com.example.memberserver.member.dto.response;

import lombok.Getter;

@Getter
public class ResponsePointDto {

    private Long point;

    public ResponsePointDto(Long point) {
        this.point = point;
    }
}
