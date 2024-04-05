package com.rko.nearbyplace.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long endTime = System.nanoTime();

        System.out.println(joinPoint.getSignature() + " executed in " + (endTime - startTime) + " ns");
        return proceed;
    }
}
