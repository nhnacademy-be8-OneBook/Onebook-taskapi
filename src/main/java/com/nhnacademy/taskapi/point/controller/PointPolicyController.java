package com.nhnacademy.taskapi.point.controller;

import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import com.nhnacademy.taskapi.point.service.PointPolicyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "포인트 정책 API", description = "포인트 정책 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/task/roles/{roleId}")
public class PointPolicyController {

    private final PointPolicyService pointPolicyService;

    @Operation(summary = "포인트 정책 생성", description = "포인트 정책을 생성합니다.")
    @PostMapping("/point-policies")
    public ResponseEntity<PointPolicyResponse> createPointPolicy(@PathVariable int roleId,
                                                                 @RequestBody CreatePointPolicyRequest policyRequest) {
        return new ResponseEntity<>(pointPolicyService.createPointPolicy(policyRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "포인트 정책 조회", description = "특정 포인트 정책을 조회합니다.")
    @GetMapping("/point-policies/{pointPolicyId}")
    public ResponseEntity<PointPolicyResponse> findPointPolicyById(@PathVariable int roleId,
                                                                   @PathVariable String pointPolicyId) {
        return new ResponseEntity<>(pointPolicyService.findPointPolicyById(pointPolicyId), HttpStatus.OK);
    }

    @Operation(summary = "포인트 정책 목록 조회", description = "모든 포인트 정책 목록을 조회합니다.")
    @GetMapping("/point-policies")
    public ResponseEntity<Page<PointPolicyResponse>> findAllPointPolicies(@PathVariable int roleId,
                                                                          Pageable pageable) {
        return new ResponseEntity<>(pointPolicyService.findAllPointPolicies(pageable), HttpStatus.OK);
    }

    @Operation(summary = "포인트 정책 수정", description = "특정 포인트 정책을 수정합니다.")
    @PutMapping("/point-policies/{pointPolicyId}")
    public ResponseEntity<PointPolicyResponse> updatePointPolicy(@PathVariable int roleId,
                                                                 @PathVariable String pointPolicyId,
                                                                 @RequestBody PointPolicyRequest policyRequest) {
        return new ResponseEntity<>(pointPolicyService.updatePointPolicyById(pointPolicyId, policyRequest), HttpStatus.OK);
    }

    @Operation(summary = "포인트 정책 삭제", description = "특정 포인트 정책을 삭제합니다.")
    @DeleteMapping("/point-policies/{pointPolicyId}")
    public ResponseEntity<Void> deletePointPolicy(@PathVariable int roleId,
                                                  @PathVariable String pointPolicyId) {
        pointPolicyService.deletePointPolicyById(pointPolicyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
