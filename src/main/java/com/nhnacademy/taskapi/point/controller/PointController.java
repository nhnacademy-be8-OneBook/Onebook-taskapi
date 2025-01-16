package com.nhnacademy.taskapi.point.controller;

import com.nhnacademy.taskapi.point.domain.PointLog;
import com.nhnacademy.taskapi.point.domain.PointLogUpdatedType;
import com.nhnacademy.taskapi.point.service.PointService;
import com.nhnacademy.taskapi.point.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/task/member/admin")
public class PointController {

    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    // 관리자가 특정 회원에게 포인트를 적립하는 API
    @PostMapping("/points/{memberId}")
    public ApiResponse<String> addPoints(@PathVariable Long memberId, @RequestParam int amount, @RequestParam String updatedType) {
        PointLogUpdatedType updatedTypeEnum = PointLogUpdatedType.valueOf(updatedType.toUpperCase());
        return pointService.addPoints(memberId, amount, updatedTypeEnum);
    }

    // PointController.java
    @GetMapping("/myPoint")
    public ModelAndView getUserPointHistories(HttpSession session) {
        // 로그인 체크
        if (session.getAttribute("user") == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView("mypage/mypagePoint");

        try {
            List<PointLog> pointLogs = pointService.getPointLogsByMember((Long) session.getAttribute("userId"));
            modelAndView.addObject("pointLogs", pointLogs);
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "포인트 내역을 가져오는 데 실패했습니다.");
        }

        return modelAndView;
    }

}
