package br.com.viajaai.viajaai.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.viajaai.viajaai.exceptions.TravelPlanNaoEncontradoException;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.services.SugestaoRoteirosService;


@RestController
@RequestMapping("/sugestao-de-roteiros")
public class SugestaoRoteirosController {
    
    private final SugestaoRoteirosService service;

    public SugestaoRoteirosController(SugestaoRoteirosService service) {
        this.service = service;
    }

    @GetMapping("/preferencias/{userId}")
    public String gerarTextoPreferencias(@PathVariable UUID userId) throws UsuarioNaoEncontradoException {
        return service.gerarRoteiroPreferencias(userId);
    }

    @GetMapping("/planos/{travelPlanId}")
    public String gerarTextoTravelPlan(@PathVariable UUID travelPlanId) throws TravelPlanNaoEncontradoException {
        return service.gerarRoteiroTravelPlan(travelPlanId);
    }
}
