package br.com.estudaai.controller;

import br.com.estudaai.service.UserService;
import br.com.planejaai.framework.controller.BaseUserController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseUserController {
  public UserController(UserService userService) {
    super(userService);
  }
}
