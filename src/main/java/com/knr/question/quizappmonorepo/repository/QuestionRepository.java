package com.knr.question.quizappmonorepo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.knr.question.quizappmonorepo.entities.Category;
import com.knr.question.quizappmonorepo.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long>{
	@Query(value = "SELECT * FROM question q WHERE q.category = :category AND q.difficulty = :difficulty ORDER BY RAND() LIMIT 3", nativeQuery = true)
	List<Long> findRandomQuestionsByCategoryAndLevel(String category, String difficulty);

	List<Question> findByCategory(Category category);
	
}