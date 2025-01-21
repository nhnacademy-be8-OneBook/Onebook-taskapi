//package com.nhnacademy.taskapi.config.schedule.scheduler;
//
//import com.nhnacademy.taskapi.config.schedule.job.InactiveMemberJobConfig;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JobScheduler {
//
//    private final JobLauncher jobLauncher;
//    private final InactiveMemberJobConfig inactiveMemberJobConfig;
//    private final PlatformTransactionManager transactionManager;
//    private final JobRepository jobRepository;
//
//    @Scheduled(cron = "0 * * * * *")
//    public void run() {
//        log.info("휴면 멤버 전환 스케줄러 동작");
//        try {
//            jobLauncher.run(inactiveMemberJobConfig.inactiveMemberJob(jobRepository, transactionManager), new JobParameters());
//        } catch (Exception e) {
//            log.error("3개월 간 로그인하지 않은 회원을 비회원으로 전환 중 에러가 발생했습니다: " + e.getMessage());
//        }
//    }
//}
