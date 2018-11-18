package com.travel.service.service;

public interface ExceptionService {
    Long saveException(Throwable e);

    void createException(String message);
}
