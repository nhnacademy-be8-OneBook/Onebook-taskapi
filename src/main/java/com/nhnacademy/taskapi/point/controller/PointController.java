package com.nhnacademy.taskapi.point.controller;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.request.UpdatePointRequest;
import com.nhnacademy.taskapi.point.response.PointResponse;
import com.nhnacademy.taskapi.point.response.UpdatePointResponse;
import com.nhnacademy.taskapi.point.service.PointService;
import com.nhnacademy.taskapi.point.request.UpdateRefundRequest;
import com.nhnacademy.taskapi.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "포인트 API", description = "포인트 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class PointController {

    private final PointService pointService;
    private final MemberService memberService;

    // 현재 포인트 조회
    @Operation(summary = "현재 포인트 조회", description = "특정 회원의 현재 포인트를 조회합니다.")
    @GetMapping("/points")
    public ResponseEntity<PointResponse> getPoints(HttpServletRequest request) {
        // HttpServletRequest에서 인증된 회원 정보 추출
        Member member = memberService.getAuthenticatedMember(request); // Authorization 헤더에서 로그인 정보를 가져와 인증
        String memberId = member.getId();
        return new ResponseEntity<>(pointService.findPointByMemberId(memberId), HttpStatus.OK);
    }

    // 포인트 사용 및 적립 내역 갱신
    @Operation(summary = "포인트 사용 및 적립 내역 갱신", description = "특정 회원의 포인트 사용 및 적립 내역을 갱신합니다.")
    @PatchMapping("/points")
    public ResponseEntity<UpdatePointResponse> updatePoint(@RequestBody UpdatePointRequest pointRequest, HttpServletRequest request) {
        // HttpServletRequest에서 인증된 회원 정보 추출
        Member member = memberService.getAuthenticatedMember(request); // Authorization 헤더에서 로그인 정보를 가져와 인증
        String memberId = member.getId();
        return new ResponseEntity<>(pointService.updatePointByMemberId(memberId, pointRequest), HttpStatus.OK);
    }

    // 포인트 환불 적용
    @Operation(summary = "포인트 환불 적용", description = "특정 회원의 포인트 환불 적용 및 내역을 갱신합니다.")
    @PatchMapping("/points/refund")
    public ResponseEntity<Void> updatePointsRefund(@RequestBody UpdateRefundRequest refundRequest, HttpServletRequest request) {
        // HttpServletRequest에서 인증된 회원 정보 추출
        Member member = memberService.getAuthenticatedMember(request); // Authorization 헤더에서 로그인 정보를 가져와 인증
        String memberId = member.getId();

        pointService.updatePointByRefund(memberId, refundRequest);
        return ResponseEntity.ok().build();
    }
}
