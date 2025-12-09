package br.com.treinaai.dto;

import java.util.List;

public class RecommendationResponse {
    private List<String> suggestions;

    public RecommendationResponse() {}
    public RecommendationResponse(List<String> suggestions) { this.suggestions = suggestions; }
    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }
}
