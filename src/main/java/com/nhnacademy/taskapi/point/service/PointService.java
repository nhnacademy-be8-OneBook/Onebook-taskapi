package com.nhnacademy.taskapi.point.service;

import com.nhnacademy.taskapi.point.request.UpdatePointRequest;
import com.nhnacademy.taskapi.point.request.UpdateRefundRequest;
import com.nhnacademy.taskapi.point.response.PointResponse;
import com.nhnacademy.taskapi.point.response.UpdatePointResponse;

public interface PointService {

    PointResponse findPointByMemberId(Long member_id);

    // 구매시 포인트 업데이트하고 현재 포인트 반환
    UpdatePointResponse updatePointByMemberId(Long member_id, UpdatePointRequest pointRequest);

    void updatePointByRefund(Long member_id, UpdateRefundRequest updateRefundRequest);

    // 포인트 결제 로직
    void usePointsForPayment(Long memberId, int paymentAmount);
}