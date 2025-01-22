//package com.nhnacademy.taskapi.point.controller;
//
//import com.nhnacademy.taskapi.point.dto.CreatePointPolicyRequest;
//import com.nhnacademy.taskapi.point.dto.PointPolicyRequest;
//import com.nhnacademy.taskapi.point.dto.PointPolicyResponse;
//import com.nhnacademy.taskapi.point.service.PointPolicyService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/task/member/admin")
//public class PointPolicyController {
//
//    private final PointPolicyService pointPolicyService;
//
//    // 포인트 정책 생성
//    @PostMapping("/point-policies")
//    public ResponseEntity<PointPolicyResponse> createPointPolicy(@RequestBody CreatePointPolicyRequest policyRequest) {
//        return new ResponseEntity<>(pointPolicyService.createPointPolicy(policyRequest), HttpStatus.CREATED);
//    }
//
//    // 포인트 정책 조회
//    @GetMapping("/point-policies/{pointPolicyId}")
//    public ResponseEntity<PointPolicyResponse> findPointPolicyById(@PathVariable Long pointPolicyId) {
//        return new ResponseEntity<>(pointPolicyService.findPointPolicyById(pointPolicyId), HttpStatus.OK);
//    }
//
//    // 포인트 정책 목록 조회
//    @GetMapping("/point-policies")
//    public ResponseEntity<Page<PointPolicyResponse>> findAllPointPolicies(Pageable pageable) {
//        return new ResponseEntity<>(pointPolicyService.findAllPointPolicies(pageable), HttpStatus.OK);
//    }
//
//    // 포인트 정책 수정
//    @PutMapping("/point-policies/{pointPolicyId}")
//    public ResponseEntity<PointPolicyResponse> updatePointPolicy(@PathVariable Long pointPolicyId, @RequestBody PointPolicyRequest policyRequest) {
//        return new ResponseEntity<>(pointPolicyService.updatePointPolicyById(pointPolicyId, policyRequest), HttpStatus.OK);
//    }
//
//    // 포인트 정책 비활성화 처리
//    @PatchMapping("/point-policies/{pointPolicyId}/deactivate")
//    public ResponseEntity<Void> deactivatePointPolicy(@PathVariable Long pointPolicyId) {
//        pointPolicyService.deactivatePointPolicy(pointPolicyId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // HTTP 204 상태 코드 반환
//    }
//}
