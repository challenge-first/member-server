package com.example.memberserver.adapter;

import com.example.memberserver.member.dto.request.RequestBidDto;
import com.example.memberserver.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BidSuccessEventConsumer {

    private final MemberService memberService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.successbid}", containerFactory = "bidKafkaListenerContainerFactory")
    public void listener(String message) throws JsonProcessingException {
        log.info("BidSuccessEventConsumer running!");
        log.info("message = {}", message);
        RequestBidDto requestBidDto = objectMapper.readValue(message, RequestBidDto.class);
        log.info("requestBidDto = {}, {}", requestBidDto.getMemberId(), requestBidDto.getBid());
        memberService.subtractPointsOnBidSuccess(requestBidDto.getMemberId(), requestBidDto.getBid());
    }
}
