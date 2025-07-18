package com.blestcodestudios.fuelsalesapp.Messaging;

import com.blestcodestudios.fuelsalesapp.config.RabbitMQConfig;
import com.blestcodestudios.fuelsalesapp.dto.HomepageVisitDto;
import com.blestcodestudios.fuelsalesapp.service.GeoIpService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


import java.util.Map;

@Component
public class HomepageVisitListener {

    private final JavaMailSender mailSender;
    private final GeoIpService geoIpService;

    public HomepageVisitListener(JavaMailSender mailSender, GeoIpService geoIpService) {
        this.mailSender = mailSender;
        this.geoIpService = geoIpService;
    }

    @RabbitListener(queues = RabbitMQConfig.HOMEPAGE_VISIT_QUEUE)
    public void receiveVisit(HomepageVisitDto visit) {
        String ip = visit.getIp();

        String location = geoIpService.lookupLocation(ip);

        String messageBody = "🧑 New homepage session started!\n\n"
                + "🕒 Time: " + visit.getTimestamp() + "\n"
                + "🌐 IP: " + visit.getIp() + "\n"
                + "📱 User-Agent: " + visit.getUserAgent()
       + "🌍 Location: " + location + "\n" +
                "🕒 Time: " + java.time.LocalDateTime.now();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("obey@sizafuel.xyz");
        email.setTo("obey@sizafuel.xyz");
        email.setSubject("🚨 New Homepage Visit - SizaFuel");
        email.setText(messageBody);

        mailSender.send(email);
        System.out.println("✅ Visit email sent for: " + visit.getIp());
    }
}