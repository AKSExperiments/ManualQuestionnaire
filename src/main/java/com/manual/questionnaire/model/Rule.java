package com.manual.questionnaire.model;

import com.manual.questionnaire.model.enums.RuleType;
import lombok.Data;

import java.util.List;

@Data
public class Rule {
	private String id;
	private RuleType type;
	private List<RuleCondition> conditions;
	private List<String> products;
}