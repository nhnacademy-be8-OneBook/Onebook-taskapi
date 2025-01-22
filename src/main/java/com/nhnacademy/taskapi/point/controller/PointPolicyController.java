package com.nhnacademy.taskapi.point.controller;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.dto.PointPolicyResponse;
import com.nhnacademy.taskapi.point.service.PointPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/point")
@RequiredArgsConstructor
public class PointPolicyController {

    private final PointPolicyService pointPolicyService;

    // 포인트 정책 업데이트
    @PutMapping("/updatePointPolicy/{pointPolicyId}")
    public ResponseEntity<PointPolicyResponse> updatePointPolicy(
            @PathVariable Long pointPolicyId,
            @RequestBody PointPolicy updatedPolicy) {

        pointPolicyService.updatePointPolicyById(pointPolicyId, updatedPolicy);
        PointPolicyResponse response = pointPolicyService.getPointPolicyById(pointPolicyId);

        return ResponseEntity.ok(response);
    }

    // 포인트 정책 삭제
    @DeleteMapping("/deletePointPolicy/{pointPolicyId}")
    public ResponseEntity<String> deletePointPolicy(@PathVariable Long pointPolicyId) {
        pointPolicyService.deactivatePointPolicy(pointPolicyId);
        return ResponseEntity.ok("포인트 정책이 삭제되었습니다.");
    }
}






//package com.nhnacademy.taskapi.point.controller;
//
//import com.nhnacademy.taskapi.point.dto.ApiResponse;
//import com.nhnacademy.taskapi.point.dto.CreatePointPolicyRequest;
//import com.nhnacademy.taskapi.point.dto.PointPolicyResponse;
//import com.nhnacademy.taskapi.point.service.PointPolicyService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/task/point")
//public class PointPolicyController {
//
//    private final PointPolicyService pointPolicyService;
//
//    // 포인트 정책 생성
//    @PostMapping("/policy")
//    public ResponseEntity<PointPolicyResponse> createPointPolicy(
//            @ModelAttribute CreatePointPolicyRequest createPointPolicyRequest) {
//        PointPolicyResponse pointPolicyResponse = pointPolicyService.createPointPolicy(createPointPolicyRequest);
//        return ResponseEntity.ok(pointPolicyResponse);
//    }
//
//    // 포인트 정책 수정
//    @PutMapping("/policy")
//    public ResponseEntity<PointPolicyResponse> updatePointPolicy(
//            @RequestHeader("X-MEMBER-ID") Long pointPolicyId,
//            @RequestBody @Valid CreatePointPolicyRequest createPointPolicyRequest) {
//        pointPolicyService.updatePointPolicyById(pointPolicyId, createPointPolicyRequest.toEntity());
//        PointPolicyResponse updatedPointPolicyResponse = pointPolicyService.getPointPolicyById(pointPolicyId);
//        return ResponseEntity.ok(updatedPointPolicyResponse);
//    }
//
//    // 포인트 정책 삭제
//    @DeleteMapping("/policy")
//    public ResponseEntity<ApiResponse<String>> deletePointPolicy(
//            @RequestHeader("X-MEMBER-ID") Long pointPolicyId) {
//        pointPolicyService.deactivatePointPolicy(pointPolicyId);
//        return ResponseEntity.ok(ApiResponse.success("Point policy deleted successfully"));
//    }
//
//    // 포인트 정책 조회 (단일)
//    @GetMapping("/policy")
//    public ResponseEntity<PointPolicyResponse> getPointPolicyById(
//            @RequestHeader("X-MEMBER-ID") Long pointPolicyId) {
//        PointPolicyResponse pointPolicyResponse = pointPolicyService.getPointPolicyById(pointPolicyId);
//        return ResponseEntity.ok(pointPolicyResponse);
//    }
//
//    // 모든 포인트 정책 조회 (페이지네이션)
//    @GetMapping("/policy/all")
//    public ResponseEntity<Page<PointPolicyResponse>> getAllPointPolicies(Pageable pageable) {
//        Page<PointPolicyResponse> pointPolicyResponses = pointPolicyService.getAllPointPolicies(pageable);
//        return ResponseEntity.ok(pointPolicyResponses);
//    }
//
//    // 모든 활성화된 포인트 정책 조회 (전체 리스트)
//    @GetMapping("/active-policies")
//    public ResponseEntity<List<PointPolicyResponse>> getActivePointPolicies() {
//        List<PointPolicyResponse> activePointPolicies = pointPolicyService.getActivePointPolicies();
//        return ResponseEntity.ok(activePointPolicies);
//    }
//}