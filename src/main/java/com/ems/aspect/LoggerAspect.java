package com.ems.aspect;

import com.ems.constants.StringConstants;
import com.ems.util.LogUtils;
import com.ems.util.LoggingLevel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


@Aspect
@Component
public class LoggerAspect {

    private final int MAX_EXCEPTION_STACK_LINE = 6;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(com.ems.annotation.MdcLog)")
    public Object logMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return handleMethodInvocation(joinPoint);
    }

    @Around("@within(com.ems.annotation.RequestResponseLog)")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        return handleControllerInvocation(joinPoint);
    }

    private Object handleControllerInvocation(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestId = request.getHeader("X-Request-ID");
        if (requestId == null) {
            MDC.put(StringConstants.LOG_ID, LogUtils.getLogId());
        } else {
            MDC.put(StringConstants.LOG_ID, requestId);
        }
        logParams(joinPoint, LoggingLevel.INFO);
        return joinPoint.proceed();
    }

//    @AfterThrowing(value = "execution(* com.ems.*.*.*(..))", throwing = "e")
//    public void allMethodException(JoinPoint joinPoint, Exception e) {
//        handleAllException(joinPoint, e);
//    }

//    private void handleAllException(JoinPoint joinPoint, Exception e) {
//        logParams(joinPoint, LoggingLevel.ERROR);
//    }

    private Object handleMethodInvocation(ProceedingJoinPoint joinPoint) throws Throwable {
        MDC.put("methodName", joinPoint.getSignature().getName());
        logParams(joinPoint, LoggingLevel.INFO);
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            logParams(joinPoint, LoggingLevel.ERROR);
            throw e;
        }
    }

    public void logParams(JoinPoint joinPoint, LoggingLevel level) {
        Object[] argsArray = joinPoint.getArgs();
        List<String> paramNames =
                new ArrayList<>(Arrays.asList(((CodeSignature) joinPoint.getSignature()).getParameterNames()));
        List<Object> paramValues = new ArrayList<>(Arrays.asList(argsArray));
        switch (level) {
            case INFO:
                logger.info(LogUtils.getFormattedLog(paramNames, paramValues));
                break;
            case DEBUG:
                logger.debug(LogUtils.getFormattedLog(paramNames, paramValues));
                break;
            case ERROR:
                logger.error(LogUtils.getFormattedLog(paramNames, paramValues));
                break;
            case WARNING:
                logger.warn(LogUtils.getFormattedLog(paramNames, paramValues));
                break;
        }
    }
}
