package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.config.RabbitMQConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;



import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VisitController {

    private final RabbitTemplate rabbitTemplate;

    public VisitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/homepage-visit")
    public void handleHomepageVisit(HttpServletRequest request) {
        Map<String, String> payload = new HashMap<>();
        payload.put("ip", request.getRemoteAddr());
        payload.put("userAgent", request.getHeader("User-Agent"));
        payload.put("timestamp", Instant.now().toString());

        rabbitTemplate.convertAndSend(RabbitMQConfig.HOMEPAGE_VISIT_QUEUE, payload);
    }
}