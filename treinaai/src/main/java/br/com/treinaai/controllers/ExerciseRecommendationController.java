package br.com.treinaai.controllers;

import br.com.treinaai.strategy.ExerciseRecommendationService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseRecommendationController {

  private final ExerciseRecommendationService exerciseService;

  public ExerciseRecommendationController(ExerciseRecommendationService exerciseService) {
    this.exerciseService = exerciseService;
  }

  @GetMapping("/recommend")
  public ResponseEntity<List<String>> recommend(
      @RequestParam String muscleGroup, @RequestParam String difficulty) {
    try {
      List<String> recommendations = exerciseService.suggestExercises(muscleGroup, difficulty);
      return ResponseEntity.ok(recommendations);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
