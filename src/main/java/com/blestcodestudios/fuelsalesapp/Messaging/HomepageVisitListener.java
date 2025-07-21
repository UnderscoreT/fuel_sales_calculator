package com.blestcodestudios.fuelsalesapp.Messaging;

import com.blestcodestudios.fuelsalesapp.config.RabbitMQConfig;
import com.blestcodestudios.fuelsalesapp.dto.HomepageVisitDto;
import com.blestcodestudios.fuelsalesapp.entity.VisitLog;
import com.blestcodestudios.fuelsalesapp.repository.VisitLogRepository;
import com.blestcodestudios.fuelsalesapp.service.EmailService;
import com.blestcodestudios.fuelsalesapp.service.GeoLookupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HomepageVisitListener {

    private final GeoLookupService geoLookupService;
    private final VisitLogRepository visitLogRepository;
    private final EmailService emailService;

    public HomepageVisitListener(
            GeoLookupService geoLookupService,
            VisitLogRepository visitLogRepository,
            EmailService emailService
    ) {
        this.geoLookupService = geoLookupService;
        this.visitLogRepository = visitLogRepository;
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMQConfig.HOMEPAGE_VISIT_QUEUE)
    public void receiveVisit(HomepageVisitDto visit) {
        // 1️⃣ lookup geo data by the IP we set above
        GeoLookupService.GeoData geoData = geoLookupService.lookup(visit.getIp());

        // 2️⃣ enrich the DTO
        visit.setCountry(geoData.country());
        visit.setCity(geoData.city());
        visit.setIsp(geoData.isp());

        // 3️⃣ save & notify
        visitLogRepository.save(new VisitLog(visit));
        emailService.sendVisitNotification(visit);

        log.info("✅ Visit saved & email sent for: {} ({}, {}, {})",
                visit.getIp(), visit.getCity(), visit.getCountry(), visit.getIsp());
    }
}
