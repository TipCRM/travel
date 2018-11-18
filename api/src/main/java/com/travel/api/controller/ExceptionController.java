package com.travel.api.controller;

import com.travel.service.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api")
public class ExceptionController {

    @Autowired
    private ExceptionService exceptionService;

    @GetMapping("exception")
    private void createException( String message) {
        exceptionService.createException(message);
    }
}
