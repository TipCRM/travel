package com.travel.service.service.impl;

import com.travel.service.config.SysConfig;
import com.travel.service.entity.SysException;
import com.travel.service.exception.BizException;
import com.travel.service.repository.ExceptionRepository;
import com.travel.service.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class ExceptionServiceImpl implements ExceptionService {

    @Autowired
    private ExceptionRepository exceptionRepository;

    @Autowired
    private SysConfig sysConfig;

    @Override
    public Long saveException(Throwable e) {
        if (e instanceof BizException) {
            return -1L;
        }
        SysException exception = buildException(e);
        Long res = 0L;
        if (sysConfig.getExceptionLogToDB()) {
            exceptionRepository.save(exception);
            res = exception.getId();
        }
        if (sysConfig.getExceptionLogToEmail()) {
            // todo: send email
        }
        return res;
    }

    @Override
    public void createException(String message) {
        throw new NullPointerException(message);
    }

    private SysException buildException(Throwable e) {
        SysException exception = new SysException();
        exception.setClassName(e.getClass().getName());
        exception.setMessage(e.getMessage());
        StringBuilder stackTrace = new StringBuilder();
        if (e.getStackTrace() != null) {
            List<StackTraceElement> elementList = CollectionUtils.arrayToList(e.getStackTrace());
            for (StackTraceElement element : elementList) {
                stackTrace.append(element.toString()).append("\n");
            }
        }
        exception.setStackTrace(stackTrace.toString());
        exception.setEntryId(0L);
        exception.setEntryDatetime(Calendar.getInstance().getTime());
        return exception;
    }
}
