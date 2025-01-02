package com.nhnacademy.taskapi.coupon.controller;

import com.nhnacademy.taskapi.coupon.domain.dto.status.request.AddPolicyStatusRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.status.request.GetPolicyStatusRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.status.response.GetPolicyStatusResponse;
import com.nhnacademy.taskapi.coupon.service.status.PolicyStatusService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Slf4j
public class PolicyStatusController {

    private final PolicyStatusService policyStatusService;

    @PostConstruct
    public void init(){

        AddPolicyStatusRequest addPolicyStatusRequestForMakeUnused = new AddPolicyStatusRequest("미사용");
        AddPolicyStatusRequest addPolicyStatusRequestForMakeUsed = new AddPolicyStatusRequest("사용됨");
        AddPolicyStatusRequest addPolicyStatusRequestForMakeDeleted = new AddPolicyStatusRequest("삭제됨");
        policyStatusService.addPolicyStatus(addPolicyStatusRequestForMakeUsed);
        policyStatusService.addPolicyStatus(addPolicyStatusRequestForMakeUnused);
        policyStatusService.addPolicyStatus(addPolicyStatusRequestForMakeDeleted);
        log.info("PolicyStatus 초기데이터 삽입 완료");
    }

    @GetMapping("/policies/status")
    public ResponseEntity<GetPolicyStatusResponse> getPolicyStatus(@RequestBody GetPolicyStatusRequest getPolicyStatusRequest){

        GetPolicyStatusResponse getPolicyStatusResponse = policyStatusService.getPolicyStatus(getPolicyStatusRequest);
        return ResponseEntity.ok(getPolicyStatusResponse);
    }

}
