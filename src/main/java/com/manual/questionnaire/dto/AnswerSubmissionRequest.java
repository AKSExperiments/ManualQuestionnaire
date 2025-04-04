package com.manual.questionnaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerSubmissionRequest {
	private String questionnaireId;
	private Map<String, Object> answers;
}