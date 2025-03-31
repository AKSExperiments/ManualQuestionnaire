package com.manual.questionnaire.controller;

import com.manual.questionnaire.model.Questionnaire;
import com.manual.questionnaire.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questionnaires")
public class QuestionnaireController {

	private final QuestionnaireService questionnaireService;

	@GetMapping("/{category}")
	public ResponseEntity<Questionnaire> getQuestionnaire(@PathVariable String category) {
		Questionnaire questionnaire = questionnaireService.getQuestionnaireByCategory(category);
		return ResponseEntity.ok(questionnaire);
	}
}