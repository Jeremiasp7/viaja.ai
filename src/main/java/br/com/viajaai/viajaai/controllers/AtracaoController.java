package br.com.viajaai.viajaai.controllers;

import br.com.viajaai.viajaai.dto.AtracaoRequestDto;
import br.com.viajaai.viajaai.dto.AtracaoResponseDto;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.services.AtracaoDescricaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atracoes")
public class AtracaoController {

    private final AtracaoDescricaoService service;

    public AtracaoController(AtracaoDescricaoService service) {
        this.service = service;
    }

    @PostMapping("/descricao")
    public ResponseEntity<AtracaoResponseDto> gerarDescricao(@RequestBody AtracaoRequestDto request) throws UsuarioNaoEncontradoException {
        AtracaoResponseDto response = service.gerarDescricao(request);
        return ResponseEntity.ok(response);
    }
}
