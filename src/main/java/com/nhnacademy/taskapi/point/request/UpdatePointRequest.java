package com.nhnacademy.taskapi.point.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdatePointRequest(BigDecimal usePoints, BigDecimal amount, String operationType) {
}
