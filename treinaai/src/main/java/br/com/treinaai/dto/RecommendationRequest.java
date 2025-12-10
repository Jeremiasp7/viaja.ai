package br.com.treinaai.dto;

import java.util.UUID;

public class RecommendationRequest {
    private UUID planId;
    private String context;
    private Integer count = 5;

    public UUID getPlanId() { return planId; }
    public void setPlanId(UUID planId) { this.planId = planId; }
    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }
    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
}
