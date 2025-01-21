package com.nhnacademy.taskapi.member.admin;

import com.nhnacademy.taskapi.member.dto.MemberResponse;
import com.nhnacademy.taskapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/admin/members")
public class MemberAdminController {
    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<Page<MemberResponse>> getAll(Pageable pageable) {
        Page<MemberResponse> memberResponse = memberService.getAllMembers(pageable);
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/{member-id}/payments/net-amount")
    public ResponseEntity<Integer> memberNetAmountPayment(@PathVariable("member-id") Long memberId) {
       Integer totalAmount = memberService.memberNetPaymentAmount(memberId);
       return ResponseEntity.ok(totalAmount);
    }
}
