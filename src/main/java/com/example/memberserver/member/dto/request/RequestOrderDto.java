package com.example.memberserver.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestOrderDto {

    private Long memberId;

    private Long price;
}
