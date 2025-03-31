package com.manual.questionnaire.service;

import com.manual.questionnaire.dto.AnswerSubmissionRequest;
import com.manual.questionnaire.dto.RecommendationResponse;
import com.manual.questionnaire.model.Rule;
import com.manual.questionnaire.model.RuleCondition;
import com.manual.questionnaire.model.enums.RuleType;
import com.manual.questionnaire.repository.RuleRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

	@Mock
	private RuleRepositoryImpl ruleRepository;

	@InjectMocks
	private RecommendationService recommendationService;

	private List<Rule> testRules;

	@BeforeEach
	void setUp() {
		testRules = new ArrayList<>();

		// Rule 1: Recommend sildenafil_50 and tadalafil_10 when q1=a2
		Rule recommendRule1 = new Rule();
		recommendRule1.setId("rule1");
		recommendRule1.setType(RuleType.RECOMMEND);
		recommendRule1.setProducts(Arrays.asList("sildenafil_50", "tadalafil_10"));

		RuleCondition condition1 = new RuleCondition();
		condition1.setQuestionId("q1");
		condition1.setAnswers(Collections.singletonList("a2"));
		recommendRule1.setConditions(Collections.singletonList(condition1));

		// Rule 2: Exclude all products when q3=a6 (taking nitrates)
		Rule excludeRule1 = new Rule();
		excludeRule1.setId("rule2");
		excludeRule1.setType(RuleType.EXCLUDE);
		excludeRule1.setProducts(Arrays.asList("sildenafil_50", "sildenafil_100", "tadalafil_10", "tadalafil_20"));

		RuleCondition condition2 = new RuleCondition();
		condition2.setQuestionId("q3");
		condition2.setAnswers(Collections.singletonList("a6"));
		excludeRule1.setConditions(Collections.singletonList(condition2));

		testRules.add(recommendRule1);
		testRules.add(excludeRule1);
	}

	@Test
	void happyPath_shouldReturnRecommendedProducts() {
		// Arrange
		when(ruleRepository.findByCategory("ed")).thenReturn(testRules);

		AnswerSubmissionRequest request = new AnswerSubmissionRequest();
		request.setQuestionnaireId("ed-questionnaire");

		Map<String, Object> answers = new HashMap<>();
		answers.put("q1", "a2");  // No hypertension
		answers.put("q3", "a7");  // Not taking nitrates
		request.setAnswers(answers);

		// Act
		RecommendationResponse response = recommendationService.generateRecommendations("ed", request);

		// Assert
		assertEquals(2, response.getRecommendedProducts().size());
		assertTrue(response.getRecommendedProducts().contains("sildenafil_50"));
		assertTrue(response.getRecommendedProducts().contains("tadalafil_10"));
	}

	@Test
	void nitratesCase_shouldExcludeAllProducts() {
		// Arrange
		when(ruleRepository.findByCategory("ed")).thenReturn(testRules);

		AnswerSubmissionRequest request = new AnswerSubmissionRequest();
		request.setQuestionnaireId("ed-questionnaire");

		Map<String, Object> answers = new HashMap<>();
		answers.put("q1", "a2");  // No hypertension
		answers.put("q3", "a6");  // Taking nitrates
		request.setAnswers(answers);

		// Act
		RecommendationResponse response = recommendationService.generateRecommendations("ed", request);

		// Assert
		assertTrue(response.getRecommendedProducts().isEmpty());
	}
}