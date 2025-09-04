package com.knr.question.quizappmonorepo.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.knr.question.quizappmonorepo.entities.Category;
import com.knr.question.quizappmonorepo.entities.Question;
import com.knr.question.quizappmonorepo.entities.QuestionWrapper;
import com.knr.question.quizappmonorepo.entities.Response;
import com.knr.question.quizappmonorepo.service.QuestionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/question")
public class QuestionController {

	private final QuestionService questionService;
	@GetMapping("/category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable Category category){
        return questionService.getQuestionsByCategory(category);
    }

	@PostMapping("/add")
	public Question addQuestion( @RequestBody Question question) {
		System.out.println(" entered end point");
		return questionService.saveQuestion(question);
	}
	@GetMapping("/generateQuestionsForQuiz")
    public List<Long> getQuestionsForQuiz(@RequestParam String category,@RequestParam String  difficulty)
    {
    	return questionService.getQuestionsForQuiz(category,difficulty);
  
    }
	@PostMapping("/getQuestions")
	public List<QuestionWrapper> getQuestionforQuizbyQuizID(@RequestBody List<Long> questionIds){
		return questionService.getQuestionforQuizbyQuizIDService(questionIds);
	}
	@PostMapping("/getScoreForQuiz")
	public Integer getScoreQuiz(@RequestBody List<Response> responses) {
		return questionService.calculateUserScore(responses);
	}
}