package cz.muni.fi.pa165.currency;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {

    @Around("execution(public * *(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        long duration = System.nanoTime();
        System.out.println("Called " + joinPoint.getSignature() + " in time: " + duration);

        Object result = joinPoint.proceed();

        duration = System.nanoTime() - duration;
        System.out.println("Duration: " + duration + " nanos");

        return result;
    }

}
