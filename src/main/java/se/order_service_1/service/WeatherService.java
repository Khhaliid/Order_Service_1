package se.order_service_1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate;
    private final String apiKey;

    public WeatherService(RestTemplate restTemplate, @Value("${weather.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public String getWeatherForCity(String city) {
        if (city == null || city.isEmpty()) {
            return null;
        }

        // Använder WeatherAPI.com istället för OpenWeatherMap
        String url = "https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city;

        try {
            WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);
            if (response != null && response.getCurrent() != null) {
                return String.format(
                        "Väder i %s: %.1f°C, %s",
                        response.getLocation().getName(),
                        response.getCurrent().getTemp_c(),
                        response.getCurrent().getCondition().getText()
                );
            }
        } catch (Exception e) {
            logger.error("Fel vid hämtning av väderdata för stad {}: {}", city, e.getMessage(), e);
        }

        return "Väderinformation ej tillgänglig";
    }

    // Klasser för att hantera WeatherAPI.com JSON-format
    private static class WeatherApiResponse {
        private Location location;
        private Current current;

        public Location getLocation() { return location; }
        public void setLocation(Location location) { this.location = location; }

        public Current getCurrent() { return current; }
        public void setCurrent(Current current) { this.current = current; }
    }

    private static class Location {
        private String name;
        private String region;
        private String country;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }

    private static class Current {
        private double temp_c;
        private Condition condition;

        public double getTemp_c() { return temp_c; }
        public void setTemp_c(double temp_c) { this.temp_c = temp_c; }

        public Condition getCondition() { return condition; }
        public void setCondition(Condition condition) { this.condition = condition; }
    }

    private static class Condition {
        private String text;
        private String icon;

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }
}