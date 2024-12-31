package com.nhnacademy.taskapi.point.response;

import java.time.LocalDateTime;

public record PointPolicyResponse(
        Long pointPolicyId,
        String pointPolicyName,
        int pointPolicyConditionAmount,
        int pointPolicyRate,
        int pointPolicyApplyAmount,
        String pointPolicyCondition,
        boolean pointPolicyApplyType,
        LocalDateTime pointPolicyCreatedAt,
        LocalDateTime pointPolicyUpdatedAt,
        boolean pointPolicyState
) {
    public static PointPolicyResponseBuilder builder() {
        return new PointPolicyResponseBuilder();
    }

    public static class PointPolicyResponseBuilder {
        private Long pointPolicyId;
        private String pointPolicyName;
        private int pointPolicyConditionAmount;
        private int pointPolicyRate;
        private int pointPolicyApplyAmount;
        private String pointPolicyCondition;
        private boolean pointPolicyApplyType;
        private LocalDateTime pointPolicyCreatedAt;
        private LocalDateTime pointPolicyUpdatedAt;
        private boolean pointPolicyState;

        public PointPolicyResponseBuilder pointPolicyId(Long pointPolicyId) {
            this.pointPolicyId = pointPolicyId;
            return this;
        }

        public PointPolicyResponseBuilder pointPolicyName(String pointPolicyName) {
            this.pointPolicyName = pointPolicyName;
            return this;
        }

        public PointPolicyResponseBuilder pointPolicyConditionAmount(int pointPolicyConditionAmount) {
            this.pointPolicyConditionAmount = pointPolicyConditionAmount;
            return this;
        }

        public PointPolicyResponseBuilder pointPolicyRate(int pointPolicyRate) {
            this.pointPolicyRate = pointPolicyRate;
            return this;
        }

        public PointPolicyResponseBuilder pointPolicyApplyAmount(int pointPolicyApplyAmount) {
            this.pointPolicyApplyAmount = pointPolicyApplyAmount;
            return this;
        }

        public PointPolicyResponseBuilder pointPolicyCondition(String pointPolicyCondition) {
            this.pointPolicyCondition = pointPolicyCondition;
            return this;
        }

        public PointPolicyResponseBuilder pointPolicyApplyType(boolean pointPolicyApplyType) {
            this.pointPolicyApplyType = pointPolicyApplyType;
            return this;
        }

        public PointPolicyResponseBuilder pointPolicyCreatedAt(LocalDateTime pointPolicyCreatedAt) {
            this.pointPolicyCreatedAt = pointPolicyCreatedAt;
            return this;
        }

        public PointPolicyResponseBuilder pointPolicyUpdatedAt(LocalDateTime pointPolicyUpdatedAt) {
            this.pointPolicyUpdatedAt = pointPolicyUpdatedAt;
            return this;
        }

        public PointPolicyResponseBuilder pointPolicyState(boolean pointPolicyState) {
            this.pointPolicyState = pointPolicyState;
            return this;
        }

        public PointPolicyResponse build() {
            return new PointPolicyResponse(pointPolicyId, pointPolicyName, pointPolicyConditionAmount,
                    pointPolicyRate, pointPolicyApplyAmount, pointPolicyCondition, pointPolicyApplyType,
                    pointPolicyCreatedAt, pointPolicyUpdatedAt, pointPolicyState);
        }
    }
}
