package com.nhnacademy.taskapi.point.controller;

import com.nhnacademy.taskapi.point.response.PointLogResponse;
import com.nhnacademy.taskapi.point.service.PointLogService;
import com.nhnacademy.taskapi.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "포인트 내역 API", description = "포인트 내역 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class PointLogController {

    private PointLogService pointLogService;

    @Operation(summary = "포인트 내역 조회", description = "특정 회원의 포인트 내역을 조회합니다.")
    @GetMapping("/point-logs")
    public ResponseEntity<Page<PointLogResponse>> getPointLogs(Pageable pageable, Member member) {
        Long member_id = member.getId();
        return new ResponseEntity<>(pointLogService.findAllPointLogsByMemberId(member_id, pageable), HttpStatus.OK);
    }
}
