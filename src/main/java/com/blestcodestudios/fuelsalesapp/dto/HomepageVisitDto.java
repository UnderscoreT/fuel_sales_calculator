package com.blestcodestudios.fuelsalesapp.dto;

import lombok.Data;

@Data
public class HomepageVisitDto {
    private String ip;
    private String userAgent;
    private String timestamp;
}