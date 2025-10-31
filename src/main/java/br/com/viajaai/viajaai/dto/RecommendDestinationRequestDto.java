package br.com.viajaai.viajaai.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendDestinationRequestDto {
   private String userPrompt;
   
   public boolean hasUserPrompt() {
      return userPrompt != null && !userPrompt.trim().isEmpty();
   }
   
   public String getUserPromptOrDefault(String defaultValue) {
      return hasUserPrompt() ? userPrompt : defaultValue;
   }
}
