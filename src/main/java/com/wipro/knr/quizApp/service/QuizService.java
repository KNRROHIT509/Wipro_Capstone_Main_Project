package com.wipro.knr.quizApp.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.wipro.knr.quizApp.entities.QuestionWrapper;
import com.wipro.knr.quizApp.entities.Quiz;
import com.wipro.knr.quizApp.entities.Response;
import com.wipro.knr.quizApp.feign.QuizInterface;
import com.wipro.knr.quizApp.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {
	@Autowired
	private final QuizRepository quizRepository;
	@Autowired
	private final QuizInterface quizInterface;
	
	public Quiz createQuiz(String category, String level,  String title) {
		List<Long> questionsIdsForQuiz=quizInterface.getQuestionsForQuiz(category, level);
		 Quiz quiz = new Quiz();
		    quiz.setQuizTitle(title);
		    quiz.setQuestionsIds(questionsIdsForQuiz);

		    return quizRepository.save(quiz);


	    /*List<Question> questions = questionRepository.findRandomQuestionsByCategoryAndLevel(category, level);

	    Quiz quiz = new Quiz();
	    quiz.setQuizTitle(title);
	    quiz.setQuestions(questions);

	    return quizRepository.save(quiz);*/
	}

	public List<QuestionWrapper> getQuizQuestions(Long id){
		Quiz quiz=quizRepository.findById(id).get();
		List<Long> questionIds=quiz.getQuestionsIds();
		return quizInterface.getQuestionforQuizbyQuizID(questionIds);
	}

	public Integer calculateResult(long id, List<Response> responses) {
		// TODO Auto-generated method stub
		return quizInterface.getScoreQuiz(responses);
	}
	
	/*@GetMapping("/getNoOfQuizs")
	public Integer getCountOfQuizs() {
		return quizInterface.getCountOfQuiz();
		
	}*/
}
