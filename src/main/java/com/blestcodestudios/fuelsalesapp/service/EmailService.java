package com.blestcodestudios.fuelsalesapp.service;

import com.blestcodestudios.fuelsalesapp.dto.HomepageVisitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("obey@sizafuel.xyz");

        mailSender.send(message);
    }

    public void sendVisitNotification(HomepageVisitDto dto) {
        String subject = "📥 Homepage Visit - " + dto.getIp();
        String body = String.format("""
                A new visitor landed on sizafuel.xyz!

                IP Address: %s
                City: %s
                Country: %s
                ISP: %s
                Time: %s
                User Agent: %s

                Visit recorded successfully 🎯
                """, dto.getIp(), dto.getCity(), dto.getCountry(), dto.getIsp(), dto.getTimestamp(), dto.getUserAgent());

        sendEmail("obey@sizafuel.xyz", subject, body);
    }
}
