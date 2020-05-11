package com.ems.aspect;

import com.ems.exception.CustomRuntimeException;
import com.ems.util.LogUtils;
import com.ems.util.LoggingLevel;
import org.apache.commons.lang3.StringEscapeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
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
import java.util.Map;
import java.util.HashMap;
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
            MDC.put(LogUtils.logId, LogUtils.getLogId());
        } else {
            MDC.put(LogUtils.logId, requestId);
        }
        logParams(joinPoint, LoggingLevel.INFO);
        return joinPoint.proceed();
    }

    @AfterThrowing(value = "execution(* com.ems.*.*.*(..))", throwing = "e")
    public void allMethodException(JoinPoint joinPoint, Exception e) {
        handleAllException(joinPoint, e);
    }

    private void handleAllException(JoinPoint joinPoint, Exception e) {
        logParams(joinPoint, LoggingLevel.ERROR);
    }

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

    private Map<String, String> getExceptionMap(Exception e) {
        Map<String, String> exceptionMap = new HashMap<>();
        String stackLog = getSomeStack(e);
        Throwable t = e.getCause();
        exceptionMap.put("class", e.getClass().toString());
        exceptionMap.put("message", e.getMessage());
        exceptionMap.put("stacktrace", StringEscapeUtils.escapeJava(stackLog));
        exceptionMap.put("cause", t == null ? null : t.getMessage());
        if (e instanceof CustomRuntimeException) {
            String developerMessage = ((CustomRuntimeException) e).getDeveloperMsg();
            developerMessage = developerMessage == null ? "" : developerMessage;
            developerMessage = developerMessage.substring(0, Math.min(developerMessage.length(), 512));
            exceptionMap.put("developerMsg", developerMessage);
        }
        return exceptionMap;
    }

    private String getSomeStack(Exception e) {
        StackTraceElement[] stack = e.getStackTrace();
        StringBuilder sbStack = new StringBuilder();
        for (int i = 0; i < MAX_EXCEPTION_STACK_LINE; i++) {
            if (stack[i] == null)
                break;
            sbStack.append(stack[i]);
            sbStack.append("    ");
        }
        return sbStack.toString();
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
