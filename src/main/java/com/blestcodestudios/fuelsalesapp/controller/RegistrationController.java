package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.dto.UserCredentialsDto;
import com.blestcodestudios.fuelsalesapp.entity.AppUser;
import com.blestcodestudios.fuelsalesapp.entity.Role;
import com.blestcodestudios.fuelsalesapp.service.CaptchaService;
import com.blestcodestudios.fuelsalesapp.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final CaptchaService            captchaService;
    private final RegistrationService       registrationService;
    private final BCryptPasswordEncoder     passwordEncoder;  // ← injected

    @GetMapping("/newuser")
    public String showRegistrationForm(Model model) {
        model.addAttribute("newUser", new UserCredentialsDto());
        return "register";
    }

    @PostMapping("/newuser")
    public String registerNewUser(
            @ModelAttribute("newUser") UserCredentialsDto dto,
            @RequestParam("g-recaptcha-response") String recaptchaResponse,
            Model model
    ) {
        System.out.println("Registering: " + dto.getUsername());


        if (!captchaService.verify(recaptchaResponse)) {
            model.addAttribute("error", "Captcha verification failed. Please try again.");
            return "register";
        }

        if (dto == null) {
            return "redirect:/register?noCreds";
        }
        if (registrationService.userExists(dto)) {
            return "redirect:/register?userExists";
        }
        if (!Objects.equals(dto.getPassword(), dto.getRePassword())) {
            return "redirect:/register?passMissMatch";
        }

        // build and save new user
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String now = dtf.format(LocalDateTime.now());

        AppUser user = new AppUser();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setEncryptedPassword(passwordEncoder.encode(dto.getPassword())); // ← use the bean
        user.setCreatedOn(now);
        user.setLastUpdated(now);




        registrationService.save(user);

        return "redirect:/index?registered=true";
    }
}
