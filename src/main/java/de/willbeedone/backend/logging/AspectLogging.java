package de.willbeedone.backend.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {

    private final Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    // -------------------- Логирование всех методов сервисов --------------------
    @Pointcut("execution(* de.willbeedone.backend.service..*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void logServiceMethodsStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        logger.info("[START] Method '{}' of class '{}' called with arguments: {}", methodName, className, args);
    }

    @After("serviceMethods()")
    public void logServiceMethodsFinish(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();

        logger.info("[END] Method '{}' of class '{}' finished execution.", methodName, className);
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logServiceMethodsSuccessfulFinish(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();

        logger.info("[SUCCESS] Method '{}' of class '{}' returned result: {}", methodName, className, result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "e")
    public void logServiceMethodsError(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();

        logger.error("[ERROR] Method '{}' of class '{}' threw exception: {}", methodName, className, e.getMessage());
    }
}