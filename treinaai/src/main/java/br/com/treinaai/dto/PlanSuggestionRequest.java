package br.com.treinaai.dto;

import java.util.UUID;

public class PlanSuggestionRequest {
    private UUID userId;
    private Integer days = 7;
    private String context;
    private UUID genericPlanId;
    private String prompt;

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }
    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }
    public UUID getGenericPlanId() { return genericPlanId; }
    public void setGenericPlanId(UUID genericPlanId) { this.genericPlanId = genericPlanId; }
    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
}
