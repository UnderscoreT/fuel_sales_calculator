package com.blestcodestudios.fuelsalesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomepageVisitDto {
    private String ip;
    private String userAgent;
    private String timestamp;

    // Add these:
    private String country;
    private String city;
    private String isp;
}