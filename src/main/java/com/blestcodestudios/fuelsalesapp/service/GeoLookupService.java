package com.blestcodestudios.fuelsalesapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.blestcodestudios.fuelsalesapp.util.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;

@Service
public class GeoLookupService {
    Logger logger = new Logger();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeoData lookup(String ip) {
        try {
            String url = "https://ipapi.co/" + ip + "/json/";
            JsonNode root = objectMapper.readTree(new URL(url));

            return new GeoData(
                    root.path("country_name").asText("Unknown"),
                    root.path("city").asText("Unknown"),
                    root.path("org").asText("Unknown")
            );
        } catch (IOException e) {
//            e.printStackTrace();
            logger.printMessage("Geo IP lookup failed for IP: "+ ip);
            return new GeoData("Unknown", "Unknown", "Unknown");
        }
    }

    public record GeoData(String country, String city, String isp) {}
}
