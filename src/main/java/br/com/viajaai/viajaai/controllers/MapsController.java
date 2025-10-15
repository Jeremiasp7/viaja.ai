package br.com.viajaai.viajaai.controllers;

import java.util.Locale;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/maps")
public class MapsController {
    
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/geocode")
    public ResponseEntity<String> geocode(@RequestParam String address) {
        String url = "https://nominatim.openstreetmap.org/search?q="
                     + address + "&format=json&limit=1";
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "viajaai");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response;
    }

    @GetMapping("/reverse")
    public ResponseEntity<String> reverse(@RequestParam double lat, @RequestParam double lon) {
        String url = "https://nominatim.openstreetmap.org/reverse?lat=" 
                     + lat + "&lon=" + lon + "&format=json";
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "viajaai");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }

    @GetMapping("/route")
    public ResponseEntity<String> getRoute(@RequestParam double lat1, @RequestParam double lon1,   @RequestParam double lat2, @RequestParam double lon2) {
        String url = String.format(Locale.US, 
        "https://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=full&geometries=geojson",
        lon1, lat1, lon2, lat2);

        return restTemplate.getForEntity(url, String.class);
    }
}
