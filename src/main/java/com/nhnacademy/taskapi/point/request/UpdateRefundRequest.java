package com.nhnacademy.taskapi.point.request;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UpdateRefundRequest(int refundAmount) {
    public boolean isRefundAmountValid() {
        return refundAmount > 0;
    }

    public int getRefundAmount() {
        return refundAmount;
    }
}
