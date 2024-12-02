package org.example.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class loggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(loggingAspect.class);

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object loggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("\nName:{}\n\tArgs:{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        try {
            Object result = joinPoint.proceed();
            logger.info("\nResult: {}\n\t", result);
            return result;
        } catch (Throwable error) {
            logger.error("\nName:{}\n\tArgs:{}", joinPoint.getSignature().getName(), joinPoint.getArgs());
            throw error;
        }
    }
}

