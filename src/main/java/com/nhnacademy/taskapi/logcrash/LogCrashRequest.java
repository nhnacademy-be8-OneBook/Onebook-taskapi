package com.nhnacademy.taskapi.logcrash;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogCrashRequest {
    String projectName;
    String projectVersion;
    String logVersion;
    String body;
    String sendTime;
    String logSource;
    String logType;
    String host;

    private static final String DEFAULT_PROJECT_NAME = "nMWnKdBvAFvUW8XL";
    private static final String DEFAULT_PROJECT_VERSION = "1.0.0";
    private static final String DEFAULT_LOG_VERSION = "v2";
    private static final String DEFAULT_LOG_SOURCE = "http";
    private static final String DEFAULT_LOG_TYPE = "nelo2-http";
    private static final String DEFAULT_HOST = "localhost";

    public LogCrashRequest(String body) {
        this.body = body;
        this.projectName = DEFAULT_PROJECT_NAME;
        this.projectVersion = DEFAULT_PROJECT_VERSION;
        this.logVersion = DEFAULT_LOG_VERSION;
        this.logSource = DEFAULT_LOG_SOURCE;
        this.logType = DEFAULT_LOG_TYPE;
        this.host = DEFAULT_HOST;
    }
}

//URL https://api-logncrash.cloud.toast.com
//secretKey cixXdhjkaHUmd8aHh7h8ns8WVICjSwXA