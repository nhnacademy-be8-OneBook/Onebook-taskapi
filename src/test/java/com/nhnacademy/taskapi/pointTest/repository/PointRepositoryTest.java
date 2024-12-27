package com.nhnacademy.taskapi.pointTest.repository;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.repository.PointPolicyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PointRepositoryTest {

    @Autowired
    private PointPolicyRepository pointPolicyRepository;

    @Test
    void testFindByPointPolicyName() {
        // @Builder를 사용하여 객체 생성
        Member member = new Member();
        PointPolicy pointPolicy = PointPolicy.builder()
                .pointPolicyName("Test Policy")
                .pointPolicyRate(10)  // 예시로 추가한 필드 값
                .pointPolicyConditionAmount(100)  // 예시로 추가한 필드 값
                .pointPolicyApplyAmount(50)  // 예시로 추가한 필드 값
                .pointPolicyCondition("Some Condition")  // 예시로 추가한 필드 값
                .pointPolicyApplyType(true)  // 예시로 추가한 필드 값
                .pointPolicyCreatedAt(java.time.LocalDate.now())  // 예시로 추가한 필드 값
                .pointPolicyState(true)  // 예시로 추가한 필드 값
                .member(member)  // 예시로 추가한 필드 값
                .build();

        // Save the pointPolicy to the repository
        pointPolicyRepository.save(pointPolicy);

        // Retrieve the PointPolicy from the repository by its name
        PointPolicy foundPolicy = pointPolicyRepository.findByPointPolicyName("Test Policy");

        // Assert the results
        assertThat(foundPolicy).isNotNull();
        assertThat(foundPolicy.getPointPolicyName()).isEqualTo("Test Policy");
    }
}