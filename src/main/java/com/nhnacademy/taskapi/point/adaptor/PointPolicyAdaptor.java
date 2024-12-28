package com.nhnacademy.taskapi.point.adaptor;

import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "pointPolicyAdaptor", url = "http://localhost:8510/members")
public interface PointPolicyAdaptor {

    // 포인트 정책 조회 (ID로 조회)
    @GetMapping("/admin/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> getPointPolicyById(@PathVariable String pointPolicyId);

    // 포인트 정책 목록 조회
    @GetMapping("/admin/point-policies")
    ResponseEntity<Page<PointPolicyResponse>> getPointPolicies(Pageable pageable);

    // 포인트 정책 생성
    @PostMapping("/admin/point-policies")
    ResponseEntity<PointPolicyResponse> createPointPolicy(@RequestBody PointPolicyRequest pointPolicyRequest);

    // 포인트 정책 수정
    @PutMapping("/admin/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> updatePointPolicy(@PathVariable String pointPolicyId,
                                                          @RequestBody PointPolicyRequest pointPolicyRequest);

    // 포인트 정책 삭제
    @DeleteMapping("/admin/point-policies/{pointPolicyId}")
    void deletePointPolicy(@PathVariable String pointPolicyId);
}
