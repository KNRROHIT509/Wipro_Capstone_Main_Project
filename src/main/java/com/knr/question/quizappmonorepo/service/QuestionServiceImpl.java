package com.knr.question.quizappmonorepo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.knr.question.quizappmonorepo.entities.Category;
import com.knr.question.quizappmonorepo.entities.Question;
import com.knr.question.quizappmonorepo.entities.QuestionWrapper;
import com.knr.question.quizappmonorepo.entities.Response;
import com.knr.question.quizappmonorepo.exception.QuestionNotFoundException;
import com.knr.question.quizappmonorepo.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionRepository repo;

    private final Environment environment;

    @Autowired
    public QuestionServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Question saveQuestion(Question question) {
        return repo.save(question);
    }

    @Override
    public Question getQuestion(Long id) {
        return repo.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + id));
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
        Question existingQuestion = repo.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question with that id is not present: " + id));

        if (q.getCategory() != null)
            existingQuestion.setCategory(q.getCategory());
        if (q.getCorrectAnswer() != null)
            existingQuestion.setCorrectAnswer(q.getCorrectAnswer());
        if (q.getDifficulty() != null)
            existingQuestion.setDifficulty(q.getDifficulty());
        if (q.getOption1() != null)
            existingQuestion.setOption1(q.getOption1());
        if (q.getOption2() != null)
            existingQuestion.setOption2(q.getOption2());
        if (q.getOption3() != null)
            existingQuestion.setOption3(q.getOption3());
        if (q.getOption4() != null)
            existingQuestion.setOption4(q.getOption4());

        return repo.save(existingQuestion);
    }

    @Override
    public List<Long> getQuestionsForQuiz(String category, String difficulty) {
        List<Long> questions = repo.findRandomQuestionsByCategoryAndLevel(category, difficulty);
        return questions;
    }

    @Override
    public List<QuestionWrapper> getQuestionforQuizbyQuizIDService(List<Long> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for (Long id : questionIds) {
            Optional<Question> questionOpt = repo.findById(id);
            if (questionOpt.isPresent()) {
                questions.add(questionOpt.get());
            } else {
                throw new QuestionNotFoundException("Question not found with id: " + id);
            }
        }

        for (Question question : questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }
        return wrappers;
    }

    @Override
    public Integer calculateUserScore(List<Response> responses) {
        log.info("The running port: {}", environment.getProperty("local.server.port"));
        int rightAnswers = 0;
        for (Response response : responses) {
            Question question = repo.findById(Long.valueOf(response.getId()))
                    .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + response.getId()));
            if (response.getUserAnswer().equals(question.getCorrectAnswer())) {
                rightAnswers++;
            }
        }
        return rightAnswers;
    }

    @Override
    public List<Question> getQuestionsByCategory(Category category) {
        return repo.findByCategory(category);
    }
}
