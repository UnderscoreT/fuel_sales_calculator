package com.blestcodestudios.fuelsalesapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.blestcodestudios.fuelsalesapp.util.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class GeoLookupService {
    Logger logger = new Logger();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeoData lookup(String ip) {
        String url = "https://ipapi.co/" + ip + "/json/";
        try {
            // Open connection with timeouts
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(2_000);
            conn.setReadTimeout(5_000);
            conn.setRequestMethod("GET");
            // optional: set a custom User-Agent
            conn.setRequestProperty("User-Agent", "SizaFuel/1.0");

            int code = conn.getResponseCode();
            InputStream in = (code >= 400) ? conn.getErrorStream() : conn.getInputStream();
            String body = new BufferedReader(new InputStreamReader(in, UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            logger.printMessage("GeoIP HTTP " + code + " for " + ip + ": " + body);

            JsonNode root = objectMapper.readTree(body);

            // Handle provider‑side errors (e.g. { "error": true, "reason": "Private Range" })
            if (root.path("error").asBoolean(false)) {
                String reason = root.path("reason").asText("unknown");
                logger.printMessage("ipapi.co error: " + reason);
                return new GeoData("Unknown","Unknown","Unknown");
            }

            return new GeoData(
                    root.path("country_name").asText("Unknown"),
                    root.path("city").asText("Unknown"),
                    root.path("org").asText("Unknown")
            );

        } catch (IOException e) {
            logger.printMessage("Geo IP lookup failed for IP: " + ip
                    + " → " + e.getClass().getSimpleName() + ": " + e.getMessage());
            return new GeoData("Unknown","Unknown","Unknown");
        }
    }


    public record GeoData(String country, String city, String isp) {}
}
