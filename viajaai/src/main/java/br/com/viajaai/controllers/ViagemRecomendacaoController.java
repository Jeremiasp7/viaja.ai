package br.com.viajaai.controllers;

import br.com.viajaai.services.ViagemRecomendacaoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/viagens/recomendacoes")
@RequiredArgsConstructor
public class ViagemRecomendacaoController {

  private final ViagemRecomendacaoService service;

  @PostMapping
  public ResponseEntity<List<String>> gerarListaDeItens(@RequestBody String contexto) {
    List<String> itens = service.gerarListaDeItens(contexto);
    return ResponseEntity.ok(itens);
  }
}