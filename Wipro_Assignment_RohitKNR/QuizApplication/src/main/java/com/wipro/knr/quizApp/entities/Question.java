package com.wipro.knr.quizApp.entities;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	
	@NotNull
	@Column(name="title")
	private String title;
	
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	
	private String correctAnswer;
	
	@Column(name="difficulty")
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;
	
	@Column(name="category")
	@Enumerated(EnumType.STRING)
	private Category category;
}
