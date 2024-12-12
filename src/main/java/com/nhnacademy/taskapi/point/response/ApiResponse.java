package com.nhnacademy.taskapi.point.response;

public record ApiResponse<T> (T body, int status) {

}
