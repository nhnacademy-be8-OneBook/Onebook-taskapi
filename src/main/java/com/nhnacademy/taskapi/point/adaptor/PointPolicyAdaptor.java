package com.nhnacademy.taskapi.point.adaptor;

import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "pointPolicyAdaptor", url = "http://localhost:8510")
public interface PointPolicyAdaptor {

    // 포인트 정책 조회 (ID로 조회)
    @GetMapping("/task/roles/{roleId}/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> getPointPolicyById(@PathVariable String pointPolicyId,
                                                           @PathVariable int roleId); // Role ID를 파라미터로 받음

    // 포인트 정책 목록 조회
    @GetMapping("/task/roles/{roleId}/point-policies")
    ResponseEntity<Page<PointPolicyResponse>> getPointPolicies(@PathVariable int roleId,
                                                               Pageable pageable); // Role ID를 파라미터로 받음

    // 포인트 정책 생성
    @PostMapping("/task/roles/{roleId}/point-policies")
    ResponseEntity<PointPolicyResponse> createPointPolicy(@PathVariable int roleId,
                                                          @RequestBody PointPolicyRequest pointPolicyRequest); // Role ID를 파라미터로 받음

    // 포인트 정책 수정
    @PutMapping("/task/roles/{roleId}/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> updatePointPolicy(@PathVariable String pointPolicyId,
                                                          @PathVariable int roleId,
                                                          @RequestBody PointPolicyRequest pointPolicyRequest); // Role ID를 파라미터로 받음

    // 포인트 정책 삭제
    @DeleteMapping("/task/roles/{roleId}/point-policies/{pointPolicyId}")
    void deletePointPolicy(@PathVariable String pointPolicyId, @PathVariable int roleId); // Role ID를 파라미터로 받음
}
