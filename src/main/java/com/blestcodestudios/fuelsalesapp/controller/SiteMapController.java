package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.dto.UserCredentialsDto;
import com.blestcodestudios.fuelsalesapp.util.Logger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class SiteMapController {
    @GetMapping({"/", "/index","/home"})
    public String getHomePage(HttpServletRequest request) {
        var message = "Remote connection from user: %s (%s)".formatted(request.getRemoteUser(), request.getRemoteAddr());
        Logger.getLogger().printMessage(message);
        return "index";
    }


    @GetMapping({"/register"})
    public String getRegistrationPage(HttpServletRequest request, Model model) {
        var message = "Remote connection from user: %s (%s)".formatted(request.getRemoteUser(), request.getRemoteAddr());
        Logger.getLogger().printMessage(message);

        var newUser = new UserCredentialsDto();
        model.addAttribute("newUser", newUser);

        return "register";
    }
    @GetMapping({"/about"})
    public String getMiscPage(HttpServletRequest request) {
        var message = "Remote connection from user: %s (%s)".formatted(request.getRemoteUser(), request.getRemoteAddr());
        Logger.getLogger().printMessage(message);
        return "about";
    }


}
