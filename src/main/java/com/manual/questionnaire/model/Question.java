package com.manual.questionnaire.model;

import lombok.Data;

import java.util.List;

@Data
public class Question {
	private String id;
	private String text;
	private List<Answer> answers;

	private String dependsOnQuestionId;
	private List<String> showWhenAnswers;
}