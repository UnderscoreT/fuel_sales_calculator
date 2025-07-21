package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.dto.UserCredentialsDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  @GetMapping("/login")
  public String showLoginPage(Model model) {
    model.addAttribute("newUser", new UserCredentialsDto());
    return "login"; // login.html
  }
  @GetMapping("/loginSuccess")
  public String loginSuccess(
          @AuthenticationPrincipal OidcUser oidcUser,
          Model model) {

    // Get the full name or first name:
    String fullName = oidcUser.getAttribute("name");           // e.g. "Obey Blessing Sibanda"
    // String firstName = oidcUser.getAttribute("given_name"); // e.g. "Obey"

    model.addAttribute("userName", fullName);
    return "index";  // or whatever view you return
  }
}
