package com.wipro.knr.quizApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.wipro.knr.quizApp.entities.Category;
import com.wipro.knr.quizApp.entities.Difficulty;
import com.wipro.knr.quizApp.entities.Question;
import com.wipro.knr.quizApp.repository.QuestionRepository;

@SpringBootApplication
@EnableAspectJAutoProxy
public class QuizApplication implements ApplicationRunner {
	
	@Autowired
	private QuestionRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
		System.out.println("Application Running");
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Question q = new Question(1, "which country introduced the Java langauge","india", "usa", "japan", "china","india", Difficulty.EASY, Category.JAVA);
		repo.save(q);
	}
}
