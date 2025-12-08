package br.com.treinaai.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planejaai.framework.controller.UserPreferencesController;
import br.com.treinaai.entities.PreferencesEntity;
import br.com.treinaai.services.PreferencesService;

@RestController
@RequestMapping("/api/preferences")
public class PreferencesController extends UserPreferencesController<PreferencesEntity>{
	
	public PreferencesController(PreferencesService service) {
		super(service);
	}

    @Override
    public PreferencesEntity getByUserId(@PathVariable UUID userId) {
        return service.buscarPreferenciasDoUsuario(userId);
    }

    @Override
    public PreferencesEntity update(@PathVariable UUID userId, @RequestBody PreferencesEntity preferences) {
        return service.atualizarPreferencias(userId, preferences);
    }

    @Override
    public PreferencesEntity create(@PathVariable UUID userId, @RequestBody PreferencesEntity preferences) {
        return service.atualizarPreferencias(userId, preferences);
    }
}
