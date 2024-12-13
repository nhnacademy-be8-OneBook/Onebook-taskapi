package com.nhnacademy.taskapi.points.service;

import com.nhnacademy.taskapi.points.request.UpdatePointRequest;
import com.nhnacademy.taskapi.points.request.UpdateRefundRequest;
import com.nhnacademy.taskapi.points.response.PointResponse;
import com.nhnacademy.taskapi.points.response.UpdatePointResponse;

public interface PointService {

    PointResponse findPointByMemberId(Long member_id);

    // 구매시 포인트 업데이트하고 현재 포인트 반환
    UpdatePointResponse updatePointByMemberId(Long member_id, UpdatePointRequest pointRequest);

    void updatePointByRefund(Long member_id, UpdateRefundRequest updateRefundRequest);
}