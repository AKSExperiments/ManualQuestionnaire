package com.manual.questionnaire.service;

import com.manual.questionnaire.model.Questionnaire;
import com.manual.questionnaire.repository.QuestionnaireRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {

	private final QuestionnaireRepositoryImpl questionnaireRepository;

	public Questionnaire getQuestionnaireByCategory(String category) {
		return questionnaireRepository.findByCategory(category)
			.orElseThrow(() -> new RuntimeException("Questionnaire not found for category: " + category));
	}
}