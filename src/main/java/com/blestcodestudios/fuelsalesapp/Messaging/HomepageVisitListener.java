package com.blestcodestudios.fuelsalesapp.Messaging;

import com.blestcodestudios.fuelsalesapp.config.RabbitMQConfig;
import com.blestcodestudios.fuelsalesapp.dto.HomepageVisitDto;
import com.blestcodestudios.fuelsalesapp.entity.VisitLog;
import com.blestcodestudios.fuelsalesapp.repository.VisitLogRepository;
import com.blestcodestudios.fuelsalesapp.service.GeoIpService;
import com.blestcodestudios.fuelsalesapp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HomepageVisitListener {

    private final GeoIpService geoIpService;
    private final VisitLogRepository visitLogRepository;
    private final EmailService emailService;

    public HomepageVisitListener(
            GeoIpService geoIpService,
            VisitLogRepository visitLogRepository,
            EmailService emailService
    ) {
        this.geoIpService       = geoIpService;
        this.visitLogRepository = visitLogRepository;
        this.emailService       = emailService;
    }

    @RabbitListener(queues = RabbitMQConfig.HOMEPAGE_VISIT_QUEUE)
    public void receiveVisit(HomepageVisitDto visit) {
        String ip = visit.getIp();
        String ua = (visit.getUserAgent() != null ? visit.getUserAgent().toLowerCase() : "");

        // ——— Skip loopback & common private ranges ———
        if (ip == null || ip.equals("127.0.0.1") || ip.equals("::1") ||
                ip.startsWith("10.")       || ip.startsWith("192.168.") ||
                ip.matches("^172\\.(1[6-9]|2\\d|3[0-1])\\..*")) {
            log.debug("Skipping private‑range visit: {}", ip);
            return;
        }

        // ——— Skip obvious bots/health‑checks ———
        if (ua.contains("curl")   || ua.contains("bot") ||
                ua.contains("health") || ua.contains("monitor")) {
            log.debug("Skipping non‑human User‑Agent: {}", visit.getUserAgent());
            return;
        }

        // ——— Perform the lookup ———
        String loc = geoIpService.lookupLocation(ip);
        if (!"Unknown location".equals(loc) && loc.contains("(")) {
            int p      = loc.indexOf('(');
            String pre = loc.substring(0, p).trim();               // "city, regionName, country"
            String isp = loc.substring(p + 1, loc.length() - 1);   // inside parentheses
            String[] parts = pre.split(",\\s*");

            visit.setCity(    parts.length > 0 ? parts[0] : "Unknown");
            visit.setCountry(parts.length > 2 ? parts[2] : "Unknown");
            visit.setIsp(     isp);
        } else {
            visit.setCity(   "Unknown");
            visit.setCountry("Unknown");
            visit.setIsp(    "Unknown");
        }

        // ——— Save & notify ———
        visitLogRepository.save(new VisitLog(visit));
        emailService.sendVisitNotification(visit);

        log.info("✅ Visit saved & email sent for: {} ({}, {}, {})",
                ip, visit.getCity(), visit.getCountry(), visit.getIsp());
    }
}
