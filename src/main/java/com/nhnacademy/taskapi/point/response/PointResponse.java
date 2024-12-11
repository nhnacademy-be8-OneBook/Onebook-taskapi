package com.nhnacademy.taskapi.point.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PointResponse(BigDecimal point) {
}
