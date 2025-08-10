package com.wipro.knr.quizApp.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.wipro.knr.quizApp.entities.QuestionWrapper;
import com.wipro.knr.quizApp.entities.Response;


@FeignClient(name="Questions-Service",path="/api/v1/question")
public interface QuizInterface {
	
	@GetMapping("/generateQuestionsForQuiz")
    public List<Long> getQuestionsForQuiz(@RequestParam String category,@RequestParam String  difficulty);
	@PostMapping("/getQuestions")
	public List<QuestionWrapper> getQuestionforQuizbyQuizID(@RequestBody List<Long> questionIds);
	@PostMapping("/getScoreForQuiz")
	public Integer getScoreQuiz(@RequestBody List<Response> responses);
    
}
