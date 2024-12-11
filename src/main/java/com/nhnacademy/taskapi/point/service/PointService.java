package com.nhnacademy.taskapi.point.service;

import com.nhnacademy.taskapi.point.request.UpdatePointRequest;
import com.nhnacademy.taskapi.point.response.PointResponse;
import com.nhnacademy.taskapi.point.response.UpdatePointResponse;

public interface PointService {
    PointResponse findPointByUserId(Long userId);
    // 구매시 포인트 업데이트하고 현재 포인트 반환
    UpdatePointResponse updatePointByUserId(Long userId, UpdatePointRequest pointRequest);
}