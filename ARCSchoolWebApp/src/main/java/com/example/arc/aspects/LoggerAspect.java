package com.example.arc.aspects;

import java.time.Duration;
import java.time.Instant;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
//Aspect
public class LoggerAspect {
	Logger log = org.slf4j.LoggerFactory.getLogger(LoggerAspect.class);
	
	//Around is Advice with pointcut expression 
	@Around("execution(* com.example.arc..*.*(..))")
	public Object log(ProceedingJoinPoint jp) throws Throwable {
		log.info(jp.getSignature().toString() +" execution starts");
		//Start time
		Instant start=Instant.now();
		//execute method
		Object obj = jp.proceed();
		//End Time
		Instant end = Instant.now();
		//Time taken to execute
		long time = Duration.between(start, end).toMillis();
		//Log with time
		log.info("Time taken by "+jp.getSignature().getName() +" for execution is :"+time);
		log.info(jp.getSignature().toLongString()+" execution ends");
		return obj;//Return back to Parent
	}
	
	//Advice
	@AfterThrowing(value = "execution(* com.example.arc.*.*(..))", throwing = "ex")
	public void logException(JoinPoint jp,Exception ex) {
		log.error("Exception thrown by "+jp.getSignature().toString() +" : "+ex.getMessage());
	}
	
}
