package com.nhnacademy.taskapi.point.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdatePointRequest(
        @Min(value = 0, message = "Use points cannot be negative.")
        int usePoints,

        @Min(value = 0, message = "Amount must be positive.")
        int amount,

        @NotBlank(message = "Operation type is required.")
        OperationType operationType
) {
    public enum OperationType {
        ADD,
        USE
    }
    public boolean isValidOperationType() {
        return operationType != null && (operationType == OperationType.ADD || operationType == OperationType.USE);
    }
}
