package com.wipro.knr.quizApp.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {
	private int id;
	private String userAnswer;
}