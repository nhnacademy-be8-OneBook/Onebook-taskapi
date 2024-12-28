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
    @GetMapping("/admin/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> getPointPolicy(@PathVariable String pointPolicyId);

    @GetMapping("/admin/point-policies")
    ResponseEntity<Page<PointPolicyResponse>> getPointPolicies(Pageable pageable);

    @PostMapping("/admin/point-policies")
    ResponseEntity<PointPolicyResponse> createPointPolicy(@RequestBody PointPolicyRequest pointPolicyRequest);

    @PutMapping("/admin/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> updatePointPolicy(@PathVariable String pointPolicyId, @RequestBody PointPolicyRequest pointPolicyRequest);

    @DeleteMapping("/admin/point-policies/{pointPolicyId}")
    void deletePointPolicy(@PathVariable String pointPolicyId);

    @GetMapping("/admin/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> findPointPolicyById(@PathVariable String pointPolicyId);
}