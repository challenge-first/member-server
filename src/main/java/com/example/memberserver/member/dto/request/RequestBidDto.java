package com.example.memberserver.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBidDto {

    private Long memberId;

    private Long bid;
}