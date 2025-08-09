package com.wipro.knr.quizApp.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wipro.knr.quizApp.entities.Category;
import com.wipro.knr.quizApp.entities.Question;
import com.wipro.knr.quizApp.exception.QuestionNotFoundException;
import com.wipro.knr.quizApp.repository.QuestionRepository;


@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository repo;

	@Override
	public Question saveQuestion(Question question) {
		return repo.save(question);
	}
	public List<Question> getQuestionsByCategory(Category category) {
		
        return repo.findByCategory(category);

	}

	@Override
	public Question getQuestion(Long id) {
		return repo.findById(id).get();
	}

	@Override
	public Page<Question> findAllQuestions(Pageable pg) {
		return repo.findAll(pg);
	}

	@Override
	public void deleteQuestion(Long id) {
		repo.deleteById(id);
	}

	@Override
	public Question patchQuestionById(Long id, Question q) {
		Question existsedQuestion = repo.findById(id)
				.orElseThrow(() -> new QuestionNotFoundException("Question with that id is not present" + id));

		if (q.getCategory() != null)
			existsedQuestion.setCategory(q.getCategory());
		if (q.getCorrectAnswer() != null)
			existsedQuestion.setCorrectAnswer(q.getCorrectAnswer());
		if (q.getDifficulty() != null)
			existsedQuestion.setDifficulty(q.getDifficulty());
		if (q.getId() != 0)
			existsedQuestion.setId(id);
		if (q.getOption1() != null)
			existsedQuestion.setOption1(q.getOption1());
		if (q.getOption2() != null)
			existsedQuestion.setOption2(q.getOption2());
		if (q.getOption3() != null)
			existsedQuestion.setOption3(q.getOption3());
		if (q.getOption4() != null)
			existsedQuestion.setOption4(q.getOption4());

		return repo.save(existsedQuestion);
	}
}
