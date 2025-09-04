package com.knr.question.quizappmonorepo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.knr.question.quizappmonorepo.entities.Category;
import com.knr.question.quizappmonorepo.entities.Question;
import com.knr.question.quizappmonorepo.entities.QuestionWrapper;
import com.knr.question.quizappmonorepo.entities.Response;

public interface QuestionService {

	Question saveQuestion(Question q);
	Question getQuestion(Long id);
	Page<Question> findAllQuestions(Pageable pg);
	void deleteQuestion(Long id);
	Question patchQuestionById(Long id ,Question q);
	List<Long> getQuestionsForQuiz(String category, String difficulty);
	List<QuestionWrapper> getQuestionforQuizbyQuizIDService(List<Long> questionIds);
	Integer calculateUserScore(List<Response> responses);
	List<Question> getQuestionsByCategory(Category category);

}
