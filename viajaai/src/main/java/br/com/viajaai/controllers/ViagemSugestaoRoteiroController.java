package br.com.viajaai.controllers;

import br.com.viajaai.services.ViagemSugestaoRoteiro;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/viagens/roteiros")
@RequiredArgsConstructor
public class ViagemSugestaoRoteiroController {

  private final ViagemSugestaoRoteiro service;

  @PostMapping("/plano/{planId}")
  public ResponseEntity<String> gerarRoteiroPeloPlano(@PathVariable("planId") UUID planId) {
    String roteiro = service.generatePlanWithGenericPlan(planId);
    return ResponseEntity.ok(roteiro);
  }

  @PostMapping("/usuario/{userId}")
  public ResponseEntity<String> gerarRoteiroPorPreferencias(
      @PathVariable("userId") UUID userId, 
      @RequestBody String promptUsuario) {
    
    String roteiro = service.generatePlanWithPreferences(userId, promptUsuario);
    return ResponseEntity.ok(roteiro);
  }
}