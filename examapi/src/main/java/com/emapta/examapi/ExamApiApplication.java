package com.emapta.examapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.emapta"}, exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class ExamApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamApiApplication.class, args);
	}

}
