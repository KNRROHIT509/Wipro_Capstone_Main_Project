package com.wipro.knr.quizApp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.knr.quizApp.entities.QuestionWrapper;
import com.wipro.knr.quizApp.entities.Quiz;
import com.wipro.knr.quizApp.entities.Response;
import com.wipro.knr.quizApp.service.QuizService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {

	private final QuizService quizService;

	@PostMapping("/create")
	@CircuitBreaker(name="createQuizCircuitBreaker", fallbackMethod ="createQuizFallBackMethod")
	public Quiz createQuiz(

			@RequestParam String category,

			@RequestParam String difficulty,

			@RequestParam String title) {

			return quizService.createQuiz(category, difficulty, title);
	}
	
	public Quiz createQuizFallBackMethod(String category,String difficulty,String title,Throwable throwable) {
		Quiz fallBackQuiz=new Quiz();	
		fallBackQuiz.setQuizTitle("our server is down !please try after some time");
		return fallBackQuiz;
	}

	@GetMapping("/getQuizByID/{id}")
	public List<QuestionWrapper> getQuizQuestions (@PathVariable Long id) {
		return quizService.getQuizQuestions(id);
	}

	@PostMapping("/submitQuiz/{id}")
	public Integer submitQuiz(@PathVariable long id, @RequestBody List<Response> responses){
		return quizService.calculateResult(id, responses);
	}

}