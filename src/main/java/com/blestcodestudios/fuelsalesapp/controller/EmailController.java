package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    @ResponseBody
    @GetMapping("/send-email")
    public String sendEmail() {
        emailService.sendEmail("obeysibanda235@gmail.com", "Test", "Hello from SizaFuel");
        return "email sent";
    }
    @PostMapping("/contact")
    @ResponseBody
    public String handleContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message) {


        try{
        String subject = "New Contact Message from "  + name;
        String content = "From: " + name + "\nEmail: " + email + "\n\nMessage: " + message;
//        emailService.sendEmail(subject, content, email);
        emailService.sendEmail("obey@sizafuel.xyz",subject,content);
            log.info("Email sent");
            return "Message sent successfully";
        } catch (Exception e){

            log.error("Failed to send email", e);
            return "Failed to send email";
        }

    }

}

