package com.nhnacademy.taskapi.point.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record UpdatePointRequest(int usePoints, int amount, String operationType) {

}
