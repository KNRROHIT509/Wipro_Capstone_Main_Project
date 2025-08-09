package com.wipro.knr.quizApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wipro.knr.quizApp.entities.Category;
import com.wipro.knr.quizApp.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long>{
	@Query(value = "SELECT * FROM question q WHERE q.category = :category AND q.difficulty = :difficulty ORDER BY RAND() LIMIT 3", nativeQuery = true)
	List<Question> findRandomQuestionsByCategoryAndLevel(String category, String difficulty);

	List<Question> findByCategory(Category category);

}