package com.manual.questionnaire.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manual.questionnaire.model.Questionnaire;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionnaireRepositoryImpl {

	private final ObjectMapper objectMapper;
	private final Map<String, Questionnaire> questionnairesByCategory = new HashMap<>();

	@PostConstruct
	public void init() {
		try {
			// Load the ED questionnaire
			ClassPathResource resource = new ClassPathResource("static/questionnaires/ed-questionnaire.json");
			Questionnaire edQuestionnaire = objectMapper.readValue(resource.getInputStream(), Questionnaire.class);

			questionnairesByCategory.put(edQuestionnaire.getCategory().toLowerCase(), edQuestionnaire);

		} catch (IOException e) {
			throw new RuntimeException("Failed to load questionnaire data", e);
		}
	}

	public Optional<Questionnaire> findByCategory(String category) {
		return Optional.ofNullable(questionnairesByCategory.get(category.toLowerCase()));
	}
}