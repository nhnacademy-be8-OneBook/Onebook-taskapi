package com.nhnacademy.taskapi.points.controller;

import com.nhnacademy.taskapi.points.request.UpdatePointRequest;
import com.nhnacademy.taskapi.points.response.PointResponse;
import com.nhnacademy.taskapi.points.response.UpdatePointResponse;
import com.nhnacademy.taskapi.points.service.PointService;
import com.nhnacademy.taskapi.points.request.UpdateRefundRequest;
import com.nhnacademy.taskapi.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "포인트 API", description = "포인트 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class PointController {
    private final PointService pointService;

    // 현재 포인트 조회
    @Operation(summary = "현재 포인트 조회", description = "특정 회원의 현재 포인트를 조회합니다.")
    @GetMapping("/points")
    public ResponseEntity<PointResponse> getPoints() {
        // Authentication 객체에서 회원 정보 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal(); // Member 객체를 가져오기
        Long memberId = member.getId();
        return new ResponseEntity<>(pointService.findPointByMemberId(memberId), HttpStatus.OK);
    }

    // 포인트 사용 및 적립 내역 갱신
    @Operation(summary = "포인트 사용 및 적립 내역 갱신", description = "특정 회원의 포인트 사용 및 적립 내역을 갱신합니다.")
    @PatchMapping("/points")
    public ResponseEntity<UpdatePointResponse> updatePoint(@RequestBody UpdatePointRequest pointRequest) {
        // Authentication 객체에서 회원 정보 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal(); // Member 객체를 가져오기
        Long memberId = member.getId();
        return new ResponseEntity<>(pointService.updatePointByMemberId(memberId, pointRequest), HttpStatus.OK);
    }

    // 포인트 환불 적용
    @Operation(summary = "포인트 환불 적용", description = "특정 회원의 포인트 환불 적용 및 내역을 갱신합니다.")
    @PatchMapping("/points/refund")
    public ResponseEntity<Void> updatePointsRefund(@RequestBody UpdateRefundRequest refundRequest) {
        // Authentication 객체에서 회원 정보 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal(); // Member 객체를 가져오기
        Long memberId = member.getId();
        pointService.updatePointByRefund(memberId, refundRequest);
        return ResponseEntity.ok().build();
    }
}
