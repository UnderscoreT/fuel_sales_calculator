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
//    @GetMapping({"/about"})
//    public String getAboutPage(HttpServletRequest request) {
//        var message = "Remote connection from user: %s (%s)".formatted(request.getRemoteUser(), request.getRemoteAddr());
//        Logger.getLogger().printMessage(message);
//        return "about";
//    }

//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "login"; // maps to login.html in your templates
//    }
//

    @GetMapping("/terms")
    public String showTerms() {
        return "terms";
    }

    @GetMapping("/privacy")
    public String showPrivacyPolicy() {
        return "privacy";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }


    @GetMapping("/blog")
    public String showBlogPage() {
        return "blog";
    }

    @GetMapping("/blog/oil-change-basics-attendants")
    public String showBlogOilChangeBasicsAttendants() {
        return "oil-change-basics-attendants";
    }

    @GetMapping("/blog/spot-fake-usd-notes")
    public String showBlogsNotes() {
        return "spot-fake-usd-notes";
    }
    @GetMapping("/blog/record-keeping-fuel-stations")
    public String showBlogRecordsKeepingFuelStations() {
        return "record-keeping-fuel-stations";
    }
    @GetMapping("/blog/customer-service-fuel-attendants")
    public String showBlogCustomerServiceFuelAttendants() {
        return "customer-service-fuel-attendants";
    }
    @GetMapping("/blog/use-a-fire-extinguisher-pass")
    public String showBlogUseAFireExtinguisherPass() {
        return "use-a-fire-extinguisher-pass";
    }
    @GetMapping("/blog/fuel-station-safety-tips")
    public String showBlogFuelStationSafetyTips() {
        return "fuel-station-safety-tips";
    }












}
