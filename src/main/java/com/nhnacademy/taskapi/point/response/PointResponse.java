package com.nhnacademy.taskapi.point.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record PointResponse(int point) {

}