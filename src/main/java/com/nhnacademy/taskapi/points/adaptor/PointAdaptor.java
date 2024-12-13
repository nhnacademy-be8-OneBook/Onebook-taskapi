package com.nhnacademy.taskapi.points.adaptor;

import com.nhnacademy.taskapi.points.response.PointLogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "pointAdaptor", url = "http://localhost:8085/point")
public interface PointAdaptor {
    @GetMapping("/points/logs/{member_id}")
    List<PointLogResponse> getPointLogsByUserId(@PathVariable("member_id") Long member_id);
}