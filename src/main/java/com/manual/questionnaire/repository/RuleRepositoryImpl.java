package com.manual.questionnaire.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manual.questionnaire.model.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RuleRepositoryImpl {

	private final ObjectMapper objectMapper;
	private final Map<String, List<Rule>> rulesByCategory = new HashMap<>();

	@PostConstruct
	public void init() {
		try {
			// Load ED rules
			ClassPathResource resource = new ClassPathResource("static/rules/ed-rules.json");
			List<Rule> edRules = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Rule>>() {});

			rulesByCategory.put("ed", edRules);

		} catch (IOException e) {
			throw new RuntimeException("Failed to load rule data", e);
		}
	}

	public List<Rule> findByCategory(String category) {
		return rulesByCategory.getOrDefault(category.toLowerCase(), new ArrayList<>());
	}
}