
package com.manual.questionnaire.model;

import lombok.Data;

import java.util.List;

@Data
public class RuleCondition {
	private String questionId;
	private List<String> answers;
}
