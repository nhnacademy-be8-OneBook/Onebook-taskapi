package com.nhnacademy.taskapi.point.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record UpdatePointRequest(
        int usePoints,  // 사용하려는 포인트 수
        int amount,  // 포인트 금액
        String operationType  // 연산 유형 (ex. 적립, 차감)
        ) { }
