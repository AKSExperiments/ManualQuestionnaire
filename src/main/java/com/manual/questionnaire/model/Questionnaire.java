package com.manual.questionnaire.model;

import lombok.Data;

import java.util.List;

@Data
public class Questionnaire {
	private String id;
	private String title;
	private String description;
	private String category;
	private String version;
	private List<Question> questions;
}