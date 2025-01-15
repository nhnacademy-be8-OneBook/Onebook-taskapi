package com.nhnacademy.taskapi.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@Slf4j
public class LogNCrash {
    private static final Logger logger = LoggerFactory.getLogger("user-logger");

    public void logging() {
        logger.debug("LogNCrash Debug.") ;

        // Log & Crash에서 예약된 항목 이외, 사용자 정의 항목 사용 시 MDC 활용
        MDC.put("userid", "nhnent-userId");
        logger.warn("Customize items...") ;
        MDC.clear();

        try {
            String logncrash = null;
            if(true) {
                System.out.print(logncrash.toString());
            }
        } catch(Exception e) {
            logger.error("LogNCrash Exception.", e);
        }
    }
}