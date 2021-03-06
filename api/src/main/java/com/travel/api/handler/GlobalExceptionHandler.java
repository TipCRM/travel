package com.travel.api.handler;


import com.travel.api.utils.JsonEntity;
import com.travel.service.entity.SysException;
import com.travel.service.exception.UnauthenticatedException;
import com.travel.service.service.ExceptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 * @author Neil Wan
 * @create 18-8-4
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private ExceptionService exceptionService;

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public JsonEntity handleUnauthenticatedException(UnauthenticatedException e) {
        JsonEntity jsonEntity = new JsonEntity();
        jsonEntity.setStatus(401);
        jsonEntity.setMessage(e.getMessage());
        return jsonEntity;
    }

    /**
     * 捕捉AccountException
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonEntity globalException(HttpServletRequest request, Throwable e) {
        logger.error("ERROR: " + e.getMessage(), e);
        // report(e);
        JsonEntity jsonEntity = new JsonEntity();
        exceptionService.saveException(e);
        jsonEntity.setStatus(getStatus(request).value());
        jsonEntity.setMessage(e.getMessage());
        return jsonEntity;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
