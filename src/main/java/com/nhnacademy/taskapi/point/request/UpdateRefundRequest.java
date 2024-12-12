package com.nhnacademy.taskapi.point.request;

import lombok.Builder;

@Builder
public record UpdateRefundRequest(int refundAmount) {

}
