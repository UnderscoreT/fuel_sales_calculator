package com.blestcodestudios.fuelsalesapp.entity;

import com.blestcodestudios.fuelsalesapp.dto.HomepageVisitDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class VisitLog {

    private  String userAgent;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;
    private String country;
    private String city;
    private String isp;
    private OffsetDateTime timestamp;


    // Constructors
    public VisitLog() {}

    public VisitLog(String ip, String country, String city, String isp, OffsetDateTime timestamp) {
        this.ip = ip;
        this.country = country;
        this.city = city;
        this.isp = isp;
        this.timestamp = timestamp;
    }

    public VisitLog(HomepageVisitDto visit) {
        this.ip = visit.getIp();
        this.userAgent = visit.getUserAgent();
        this.timestamp = OffsetDateTime.parse(visit.getTimestamp());
        this.city = visit.getCity();
        this.country = visit.getCountry();
        this.isp = visit.getIsp();
    }

    // Getters and Setters
    // (You can use Lombok here if you prefer)
}
