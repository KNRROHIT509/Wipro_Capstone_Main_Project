package com.knr.question.quizappmonorepo.entities;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "question")
public class Question {

	@Id
	private long id;
	
	@NotBlank(message="Question title is required")
	@Column(name="title")
	private String title;
	
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	
	@NotBlank(message = "Correct answer is required")
	private String correctAnswer;
	

	@Column(name="difficulty")
	private String difficulty;
	
	@Column(name="category")
	private String category;
}
