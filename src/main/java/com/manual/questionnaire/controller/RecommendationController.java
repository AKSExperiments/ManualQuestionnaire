package com.manual.questionnaire.controller;

import com.manual.questionnaire.dto.AnswerSubmissionRequest;
import com.manual.questionnaire.dto.RecommendationResponse;
import com.manual.questionnaire.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

	private final RecommendationService recommendationService;

	@PostMapping("/{category}")
	public ResponseEntity<RecommendationResponse> generateRecommendations(
		@PathVariable String category,
		@RequestBody AnswerSubmissionRequest request) {

		RecommendationResponse response = recommendationService.generateRecommendations(category, request);
		return ResponseEntity.ok(response);
	}
}