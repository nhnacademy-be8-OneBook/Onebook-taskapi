package com.nhnacademy.taskapi.point.service;

import com.nhnacademy.taskapi.point.request.UpdatePointRequest;
import com.nhnacademy.taskapi.point.response.PointResponse;
import com.nhnacademy.taskapi.point.response.UpdatePointResponse;

public interface PointService {
    // 구매시 포인트 업데이트 후, 현재 포인트 반환
    UpdatePointResponse updatePointByUserId(Long member_id, UpdatePointRequest pointRequest);
    PointResponse findPointByUserId(Long member_id);
}