package br.com.viajaai.controllers;

import br.com.planejaai.framework.controller.UserPreferencesController;
import br.com.viajaai.entities.ViagemPreferences;
import br.com.viajaai.services.ViagemPreferencesService;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viagens/preferences")
public class ViagemPreferencesController extends UserPreferencesController<ViagemPreferences> {

  public ViagemPreferencesController(ViagemPreferencesService service) {
    super(service);
  }


  @Override
  @GetMapping("/{userId}")
  public ViagemPreferences getByUserId(@PathVariable("userId") UUID userId) {
    return super.getByUserId(userId);
  }

  @Override
  @PutMapping("/{userId}")
  public ViagemPreferences update(@PathVariable("userId") UUID userId, @RequestBody ViagemPreferences preferences) {
    return super.update(userId, preferences);
  }

  @Override
  @PostMapping("/{userId}")
  public ViagemPreferences create(@PathVariable("userId") UUID userId, @RequestBody ViagemPreferences preferences) {
    return super.create(userId, preferences);
  }
}