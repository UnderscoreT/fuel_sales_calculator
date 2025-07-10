package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.dto.UserCredentialsDto;
import com.blestcodestudios.fuelsalesapp.entity.AppUser;
import com.blestcodestudios.fuelsalesapp.service.CaptchaService;
import com.blestcodestudios.fuelsalesapp.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CaptchaService captchaService;


    private final RegistrationService registrationService;
    @PostMapping("/newuser")
    public String registerNewUser(@ModelAttribute("newUser") UserCredentialsDto userCredentialsDto,
        @RequestParam("g-recaptcha-response") String recaptchaResponse,
        Model model2) {

            if (!captchaService.verify(recaptchaResponse)) {
                model2.addAttribute("error", "Captcha verification failed. Please try again.");
                return "register";
            }


        if(Objects.isNull(userCredentialsDto))return "redirect:/register?noCreds";
    if(registrationService.userExists(userCredentialsDto))
            return "redirect:/register?userExists";
    if(!Objects.equals(userCredentialsDto.getPassword(), userCredentialsDto.getRePassword()))
        return "redirect:/register?passMissMatch";
        var dtf  = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        var now = LocalDateTime.now();
        var model = new AppUser();
        model.setUsername(userCredentialsDto.getUsername());
        model.setEmail(userCredentialsDto.getEmail());
        model.setEncryptedPassword(new BCryptPasswordEncoder().encode(userCredentialsDto.getPassword()));
        //should use a js to make sure that 2 passwords really match b4 submitting
        model.setCreatedOn(dtf.format(now));
        model.setLastUpdated(model.getCreatedOn());
        registrationService.save(model);
//        return "redirect:/index";
        return "redirect:/index?registered=true";
     }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("newUser", new UserCredentialsDto());
        return "login"; // login.html
    }

}