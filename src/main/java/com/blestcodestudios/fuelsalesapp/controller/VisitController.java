package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.config.RabbitMQConfig;
import com.blestcodestudios.fuelsalesapp.util.RateLimiter;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VisitController {

    private final RabbitTemplate rabbitTemplate;
    private final Counter visitCounter;

    public VisitController(RabbitTemplate rabbitTemplate, MeterRegistry meterRegistry) {
        this.rabbitTemplate = rabbitTemplate;
        this.visitCounter = meterRegistry.counter("visits.total");
    }

    @PostMapping("/homepage-visit")
    public ResponseEntity<Void> handleHomepageVisit(
            HttpServletRequest request,
            Authentication auth            // injected by Spring Security
    ) {
        // if we got here, JWT was valid
        String clientIp = extractClientIp(request);
        if (!RateLimiter.allow(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        visitCounter.increment();

        Map<String, String> payload = new HashMap<>();
        payload.put("ip", clientIp);
        payload.put("userAgent", request.getHeader("User-Agent"));
        payload.put("timestamp", Instant.now().toString());
        payload.put("username", auth.getName());

        rabbitTemplate.convertAndSend(RabbitMQConfig.HOMEPAGE_VISIT_QUEUE, payload);
        return ResponseEntity.accepted().build();
    }

    private String extractClientIp(HttpServletRequest req) {
        String xf = req.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isBlank()) {
            return xf.split(",")[0].trim();
        }
        return req.getRemoteAddr();
    }
}
