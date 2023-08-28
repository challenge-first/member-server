package com.example.memberserver.adapter;

import com.example.memberserver.member.dto.request.RequestOrderDto;
import com.example.memberserver.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
//@Service
public class OrderEventConsumer {

    private final MemberService memberService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.order}", containerFactory = "payKafkaListenerContainerFactory")
    public void listener(String message) throws JsonProcessingException {
        log.info("OrderEventConsumer running!");
        log.info("message = {}", message);
        RequestOrderDto requestOrderDto = objectMapper.readValue(message, RequestOrderDto.class);
//        log.info("requestBidDto = {}, {}", requestOrderDto.getMemberId(), requestOrderDto.getPrice());
//        memberService.payPoint(requestOrderDto.getMemberId(), requestOrderDto.getPrice());
    }
}
