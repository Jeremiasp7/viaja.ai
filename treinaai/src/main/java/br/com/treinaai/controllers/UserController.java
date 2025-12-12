package br.com.treinaai.controllers;

import br.com.planejaai.framework.controller.BaseUserController;
import br.com.treinaai.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseUserController {

  public UserController(UserService service) {
    super(service);
  }
}
