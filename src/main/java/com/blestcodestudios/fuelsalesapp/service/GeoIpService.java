package com.blestcodestudios.fuelsalesapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class GeoIpService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String lookupLocation(String ip) {
        try {
            String url = "http://ip-api.com/json/" + ip;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null || !"success".equals(response.get("status"))) {
                return "Unknown location";
            }

            return String.format(
                "%s, %s, %s (%s)",
                response.get("city"),
                response.get("regionName"),
                response.get("country"),
                response.get("isp")
            );
        } catch (Exception e) {
            return "Location lookup failed";
        }
    }
}
