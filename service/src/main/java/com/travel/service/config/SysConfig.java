package com.travel.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class SysConfig {
    @Value("${TRAVEL_EXCEPTION_LOG_TO_DB}")
    private Boolean exceptionLogToDB;

    @Value("${TRAVEL_EXCEPTION_LOG_TO_EMAIL}")
    private Boolean exceptionLogToEmail;

    public Boolean getExceptionLogToDB() {
        return exceptionLogToDB;
    }

    public void setExceptionLogToDB(Boolean exceptionLogToDB) {
        this.exceptionLogToDB = exceptionLogToDB;
    }

    public Boolean getExceptionLogToEmail() {
        return exceptionLogToEmail;
    }

    public void setExceptionLogToEmail(Boolean exceptionLogToEmail) {
        this.exceptionLogToEmail = exceptionLogToEmail;
    }
}
