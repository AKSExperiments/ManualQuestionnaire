package com.manual.questionnaire.service;

import com.manual.questionnaire.dto.AnswerSubmissionRequest;
import com.manual.questionnaire.dto.RecommendationResponse;
import com.manual.questionnaire.model.Rule;
import com.manual.questionnaire.model.RuleCondition;
import com.manual.questionnaire.model.enums.RuleType;
import com.manual.questionnaire.repository.RuleRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecommendationService {

	private final RuleRepositoryImpl ruleRepository;

	public RecommendationResponse generateRecommendations(String category, AnswerSubmissionRequest request) {
		List<Rule> rules = ruleRepository.findByCategory(category);

		// First, find all products that should be recommended
		Set<String> recommendedProducts = new HashSet<>();
		for (Rule rule : rules) {
			if (RuleType.RECOMMEND.equals(rule.getType()) && isRuleApplicable(rule, request.getAnswers())) {
				recommendedProducts.addAll(rule.getProducts());
			}
		}

		// Then, remove any products that should be excluded
		for (Rule rule : rules) {
			if (RuleType.EXCLUDE.equals(rule.getType()) && isRuleApplicable(rule, request.getAnswers())) {
				recommendedProducts.removeAll(rule.getProducts());
			}
		}

		return new RecommendationResponse(new ArrayList<>(recommendedProducts));
	}

	private boolean isRuleApplicable(Rule rule, Map<String, Object> userAnswers) {
		// All conditions must match for the rule to be applicable
		for (RuleCondition condition : rule.getConditions()) {
			String questionId = condition.getQuestionId();
			List<String> validAnswers = condition.getAnswers();

			if (!userAnswers.containsKey(questionId)) {
				return false;
			}

			Object userAnswer = userAnswers.get(questionId);

			if (userAnswer instanceof String) {
				if (!validAnswers.contains(userAnswer)) {
					return false;
				}
			}
		}

		return true;
	}
}