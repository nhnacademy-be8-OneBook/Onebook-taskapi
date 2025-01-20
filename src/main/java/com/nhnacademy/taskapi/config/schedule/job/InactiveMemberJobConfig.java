//package com.nhnacademy.taskapi.config.schedule.job;
//
//import com.nhnacademy.taskapi.member.domain.Member;
//import com.nhnacademy.taskapi.member.repository.MemberQueryDslRepository;
//import com.nhnacademy.taskapi.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.support.ListItemReader;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.List;
//
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class InactiveMemberJobConfig {
//
//    private final MemberQueryDslRepository memberQueryDslRepository;
//    private final MemberRepository memberRepository;
//
//    @Bean("inactiveMemberJob")
//    public Job inactiveMemberJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new JobBuilder("inactiveMemberJob", jobRepository)
//                // job이 실행될 때마다 파라미터 아이디를 자동으로 실행.
//                .incrementer(new RunIdIncrementer())
//                // job 실행 시 최초로 실행될 step 지정.
//                .start(inactiveJobStep(jobRepository, transactionManager))
//                .build();
//
//    }
//
//    @JobScope // 해당 빈이 job 실행 시 한번만 생성.
//    @Bean("inactiveJobStep")
//    public Step inactiveJobStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("inactiveJobStep", jobRepository)
//                .<Member, Member>chunk(5, transactionManager)
//                .reader(inactiveMemberReader())
//                .processor(inactiveMemberProcessor())
//                .writer(inactiveMemberWriter())
//                .transactionManager(transactionManager)
//                .build();
//    }
//
//    // 3개월 간 로그인하지 않은 회원 불러오기.
//    @Bean
//    @StepScope // 배치 작업을 실행할 때마다 step마다 독립적인 인스턴스를 생성.
//    public ItemReader<Member> inactiveMemberReader() {
//        List<Member> members = memberQueryDslRepository.getAllMemberByBatch();
//        return new ListItemReader<>(members);
//    }
//
//    // 회원의 상태를 비활성화.
//    @Bean
//    public ItemProcessor<Member, Member> inactiveMemberProcessor() {
//        return member -> {
//            member.setStatus(Member.Status.INACTIVE);
//            return member;
//        };
//    }
//
//    // 데이터 저장.
//    @Bean
//    public ItemWriter<Member> inactiveMemberWriter() {
//        return memberRepository::saveAll;
//    }
//
//
//}
