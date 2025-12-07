package br.com.planejaai.framework.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import br.com.planejaai.framework.service.UserPreferencesService;

@RequestMapping("/api/preferences")
public abstract class UserPreferencesController<T extends UserPreferencesEntityAbstract> {

    protected final UserPreferencesService<T> service;

    protected UserPreferencesController(UserPreferencesService<T> service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public T getByUserId(@PathVariable UUID userId) {
        return service.buscarPreferenciasDoUsuario(userId);
    }

    @PutMapping("/{userId}")
    public T update(@PathVariable UUID userId, @RequestBody T preferences) {
        return service.atualizarPreferencias(userId, preferences);
    }

    @PostMapping("/{userId}")
    public T create(@PathVariable UUID userId, @RequestBody T preferences) {
        return service.atualizarPreferencias(userId, preferences);
    }
}