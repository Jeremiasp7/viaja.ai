package br.com.viajaai.controllers;

import br.com.viajaai.services.ViagemDicasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/viagens/dicas")
public class ViagemDicasController {

  private final ViagemDicasService dicasService;

  public ViagemDicasController(ViagemDicasService dicasService) {
    this.dicasService = dicasService;
  }

  @GetMapping
  public ResponseEntity<String> explicarConceito(@RequestParam("duvida") String duvida) {
    return ResponseEntity.ok(dicasService.recommendConcept(duvida));
  }
}