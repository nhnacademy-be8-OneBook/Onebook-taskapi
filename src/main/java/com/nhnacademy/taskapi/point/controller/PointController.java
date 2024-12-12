//수정중
/*
package com.nhnacademy.taskapi.point.controller;

import com.nhnacademy.taskapi.point.request.UpdatePointRequest;
import com.nhnacademy.taskapi.point.response.PointResponse;
import com.nhnacademy.taskapi.point.response.UpdatePointResponse;
import com.nhnacademy.taskapi.point.service.PointService;
import com.nhnacademy.taskapi.point.request.UpdateRefundRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "포인트 API", description = "포인트 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PointController {
    private final PointService pointService;

    @Operation(summary = "현재 포인트 조회", description = "특정 회원의 현재 포인트를 조회합니다.")
    @GetMapping("/points")
    public ResponseEntity<PointResponse> getPoints(@CurrentUser JwtUserDetails jwtUserDetails) {

        Long userId = jwtUserDetails.userId();

        return new ResponseEntity<>(pointService.findPointByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "포인트 사용 및 적립 내역 갱신", description = "특정 회원의 포인트 사용 및 적립 내역을 갱신합니다.")
    @PatchMapping("/points")
    public ResponseEntity<UpdatePointResponse> updatePoint(@RequestBody UpdatePointRequest pointRequest,
                                                           @CurrentUser JwtUserDetails jwtUserDetails) {

        Long userId = jwtUserDetails.userId();

        return new ResponseEntity<>(pointService.updatePointByUserId(userId, pointRequest), HttpStatus.OK);
    }

    @Operation(summary = "포인트 환불 적용", description = "특정 회원의 포인트 환불 적용 및 내역을 갱신합니다.")
    @PatchMapping("/points/refund")
    public ResponseEntity<Void> updatePointsRefund(@RequestBody UpdateRefundRequest refundRequest,
                                                   @CurrentUser JwtUserDetails jwtUserDetails) {

        Long userId = jwtUserDetails.userId();

        pointService.updatePointByRefund(userId, refundRequest);

        return ResponseEntity.ok().build();
    }
}
*/