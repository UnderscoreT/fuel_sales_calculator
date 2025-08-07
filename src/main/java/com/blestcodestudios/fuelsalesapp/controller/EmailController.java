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
import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    // ─── Block specific senders ────────────────────────────────────────────
    private static final Set<String> BLOCKED_SENDERS = Set.of(
        "kara.quesinberry93@googlemail.com"
    );

    // ─── Spam‐keyword patterns ───────────────────────────────────────────────
    private static final List<Pattern> SPAM_PATTERNS = List.of(
        Pattern.compile("\\bbitcoin\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bbtc\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bcrypto\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bforex\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bnft\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bclaim btc\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\btoxic backlinks\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bgoogle penalty\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bsubmit .*? to google\\b", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("\\bindexed\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bseo ranking\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bcheap seo\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\burgent offer\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bmoney back\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bclick to claim\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bwork from home\\b", Pattern.CASE_INSENSITIVE),
        Pattern.compile("\\bgoogle search index\\b", Pattern.CASE_INSENSITIVE),
        // catches “promo”, “promos”, “promotion”, “promotions”, “promotional”, etc.
        Pattern.compile("\\bpromo(?:tion(?:al)?s?)?\\b", Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
    );

    private static final List<String> BAD_DOMAINS = List.of(
        "graph.org", "code-gmail.com", "linktr.ee", "bit.ly"
    );

    @ResponseBody
    @GetMapping("/send-email")
    public String sendEmail() {
        emailService.sendEmail(
            "obeysibanda235@gmail.com",
            "Test",
            "Hello from SizaFuel"
        );
        return "email sent";
    }

    @PostMapping("/contact")
    @ResponseBody
    public String handleContactForm(
        @RequestParam String name,
        @RequestParam String email,
        @RequestParam String message,
        @RequestParam(required = false) String website, // honeypot
        HttpServletRequest request
    ) {
        String senderIp = request.getRemoteAddr();
        log.info("Contact form submitted from IP: {}", senderIp);

        // ─── Honeypot check ─────────────────────────────────────────────────
        if (website != null && !website.isEmpty()) {
            log.warn("Spam blocked: Honeypot triggered by IP {}", senderIp);
            return "Message sent successfully";
        }

        // ─── Blocked‐sender check ─────────────────────────────────────────────
        if (email != null &&
            BLOCKED_SENDERS.contains(email.trim().toLowerCase())
        ) {
            log.warn("Blocked sender: {} from IP {}", email, senderIp);
            return "Message sent successfully";
        }

        // ─── Spam‐keyword & domain checks ────────────────────────────────────
        if (isSpam(message, email)) {
            log.warn(
                "Spam blocked: Message from IP {} matched spam pattern or bad domain",
                senderIp
            );
            return "Message sent successfully";
        }

        // ─── Send the contact email ───────────────────────────────────────────
        try {
            String subject = "New Contact Message from " + name;
            String content =
                "From: " + name +
                "\nEmail: " + email +
                "\nIP: " + senderIp +
                "\n\nMessage: " + message;

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

        // check against each regex
        for (Pattern p : SPAM_PATTERNS) {
            if (p.matcher(message).find()) {
                log.warn("Spam blocked: matched pattern “{}”", p.pattern());
                return true;
            }
        }

        // check sender domain
        if (email != null) {
            String em = email.toLowerCase();
            for (String domain : BAD_DOMAINS) {
                if (em.contains(domain)) {
                    log.warn("Spam blocked: sender domain “{}”", domain);
                    return true;
                }
            }
        }

        return false;
    }
}