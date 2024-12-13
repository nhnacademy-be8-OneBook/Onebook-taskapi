package com.nhnacademy.taskapi.points.request;

import lombok.Builder;

@Builder
public record UpdateRefundRequest(int refundAmount) {
    public boolean isRefundAmountValid() {
        return refundAmount > 0;
    }

    public int getRefundAmount() {
        return refundAmount;
    }
}
