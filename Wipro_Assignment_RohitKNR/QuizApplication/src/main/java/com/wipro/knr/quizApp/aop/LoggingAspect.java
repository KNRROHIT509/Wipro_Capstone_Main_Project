package com.wipro.knr.quizApp.aop;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.wipro.knr.quizApp.entities.Response;

import lombok.extern.slf4j.Slf4j;

@Aspect  //extra logic
@Component //object creation
@Slf4j
public class LoggingAspect {

	@Before("execution(* com.wipro.knr.quizApp.controller.QuizController.createQuiz(..))")
    public void logBeforeCreateQuiz(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();  // Capture method arguments
        String category = (String) args[0];
        String difficulty = (String) args[1];
        String title = (String) args[2];
        log.info("AOP Triggered: Creating quiz - Category: {}, Difficulty: {}, Title: {}", category, difficulty, title);
    }

    @Before("execution(* com.wipro.knr.quizApp.controller.QuizController.submitQuiz(..))")
    public void logBeforeSubmitQuiz(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        long id = (long) args[0];
        @SuppressWarnings("unchecked")
        List<Response> responses = (List<Response>) args[1];  // Adjust based on your Response entity
        log.info("AOP Triggered: Submitting quiz ID: {} with {} responses", id, responses.size());
    }

    @Before("execution(* com.wipro.knr.quizApp.controller.QuizController.*(..))")
    public void logBeforeAnyQuizMethod(JoinPoint joinPoint) {
        log.debug("AOP Triggered: Entering method {} with args: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }	
}