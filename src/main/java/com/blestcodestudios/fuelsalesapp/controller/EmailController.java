package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.regex.Pattern;

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
            @RequestParam String message,
            @RequestParam(required = false) String website, // Honeypot field
            HttpServletRequest request) {

        String senderIp = request.getRemoteAddr();
        log.info("Contact form submitted from IP: {}", senderIp);

        // === Spam Filter Section ===
        if (website != null && !website.isEmpty()) {
            log.warn("Spam blocked: Honeypot triggered by IP {}", senderIp);
            return "Message sent successfully";
        }

        if (isSpam(message, email)) {
            log.warn("Spam blocked: Message from IP {} contained spam keywords", senderIp);
            return "Message sent successfully";
        }

        try {
            String subject = "New Contact Message from " + name;
            String content = "From: " + name + "\nEmail: " + email + "\nIP: " + senderIp + "\n\nMessage: " + message;

            emailService.sendEmail("obey@sizafuel.xyz", subject, content);
            log.info("Email sent from {}", senderIp);
            return "Message sent successfully";
        } catch (Exception e) {
            log.error("Failed to send email", e);
            return "Failed to send email";
        }
    }

    private boolean isSpam(String message, String email) {
        if (message == null) return true;

        String msg = message.toLowerCase();

        // Block too-short garbage
        if (msg.length() < 10 && !msg.matches(".*[a-zA-Z].*")) return true;

        List<String> bannedKeywords = List.of(
                "bitcoin", "btc", "crypto", "forex", "nft", "claim btc", "toxic backlinks",
                "google penalty", "submit .*? to google", "indexed", "seo ranking",
                "cheap seo", "urgent offer", "money back", "click to claim", "work from home","Google Search Index","promotional offer"
        );

        for (String keyword : bannedKeywords) {
            if (msg.matches(".*\\b" + Pattern.quote(keyword) + "\\b.*")) {
                return true;
            }
        }

        List<String> badDomains = List.of("graph.org", "code-gmail.com", "linktr.ee", "bit.ly");
        if (email != null) {
            String em = email.toLowerCase();
            for (String domain : badDomains) {
                if (em.contains(domain)) return true;
            }
        }

        return false;
    }



}



