# Medical Questionnaire API

A Spring Boot application providing APIs for medical questionnaires and product recommendations.

## API Endpoints

1. `GET /api/v1/questionnaires/{category}` - Fetches the questionnaire structure
2. `POST /api/v1/recommendations/{category}` - Processes answers and returns product recommendations

## Design Decisions

### Rule-Based System vs Tree Structure
- Selected a rule-based system over a tree structure because:
    - **Greater flexibility** for complex recommendation logic
    - **Easier maintenance** when rules need modification
    - **Better separation** between questionnaire flow and recommendation logic
    - **Simpler extensibility** for future admin functionality

### Single Payload vs Progressive API
- Chose delivering the complete questionnaire in a single response instead of question-by-question:
    - **Reduced API calls** creates a smoother user experience
    - **Better offline capability** as frontend has all questions immediately
    - **Simpler state management** on the frontend
    - **More efficient** for most questionnaire flows

### Recommendation Engine Approach
- Implemented a two-phase filtering process:
    1. Apply recommendation rules to build a product list
    2. Apply exclusion rules to remove product from the above list
- This ensures medical safety by giving exclusion rules priority

### Data Storage Strategy
- Used in-memory storage loaded from JSON files:
    - **Fast implementation** for the time constraints
    - **Clear interfaces** designed for easy replacement with database storage

## Testing the APIs

### 1. Get Questionnaire

```
GET http://localhost:8080/api/v1/questionnaires/ed
```

### 2. Submit Answers for Recommendations (Happy Path)

```
POST http://localhost:8080/api/v1/recommendations/ed
Content-Type: application/json

{
  "questionnaireId": "ed-questionnaire",
  "answers": {
    "q1": "a2",
    "q3": "a7",
    "q4": "a10"
  }
}
```

Expected response:
```json
{
  "recommendedProducts": ["sildenafil_50", "sildenafil_100", "tadalafil_10", "tadalafil_20"]
}
```

### 3. Testing the all products excluded (negative case)

```
POST http://localhost:8080/api/v1/recommendations/ed
Content-Type: application/json

{
  "questionnaireId": "ed-questionnaire",
  "answers": {
    "q1": "a2",
    "q3": "a6",
    "q4": "a10"
  }
}
```

Expected response (no products due to nitrates contraindication):
```json
{
  "recommendedProducts": []
}
```

## Setup

1. Clone the repository
2. Ensure JSON files are placed in:
    - `src/main/resources/data/questionnaires/ed-questionnaire.json`
    - `src/main/resources/data/rules/ed-rules.json`
3. Run: `mvn spring-boot:run`

## Potential Future Improvements

1. **Database Integration** - Replace in-memory storage with persistent database
2. **Enhanced Rule Engine** - Support numerical comparisons, complex conditions, and rule prioritization
3. **Admin Interface** - Add APIs for managing questionnaires and rules
4. **Versioning System** - Track and manage questionnaire changes over time
5. **Comprehensive Testing** - Add integration tests and edge case coverage
