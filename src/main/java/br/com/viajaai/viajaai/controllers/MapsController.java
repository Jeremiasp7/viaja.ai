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
    public ResponseEntity<String> getRouteByAddress(
            @RequestParam String origin,
            @RequestParam String destination) {

        try {
            var mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            var originResponse = geocode(origin);
            var originArray = mapper.readTree(originResponse.getBody());

            if (originArray.isEmpty() && origin.contains(" ")) {
                String reducedOrigin = origin.split(",")[0].trim(); 
                originResponse = geocode(reducedOrigin);
                originArray = mapper.readTree(originResponse.getBody());
            }

            if (originArray.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("{\"error\": \"Endereço de origem não encontrado.\"}");
            }

            double lat1 = originArray.get(0).get("lat").asDouble();
            double lon1 = originArray.get(0).get("lon").asDouble();

            // --- Geocode do destino ---
            var destResponse = geocode(destination);
            var destArray = mapper.readTree(destResponse.getBody());

            // Busca alternativa caso não encontre
            if (destArray.isEmpty() && destination.contains(" ")) {
                String reducedDestination = destination.split(",")[0].trim();
                destResponse = geocode(reducedDestination);
                destArray = mapper.readTree(destResponse.getBody());
            }

            if (destArray.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("{\"error\": \"Endereço de destino não encontrado.\"}");
            }

            double lat2 = destArray.get(0).get("lat").asDouble();
            double lon2 = destArray.get(0).get("lon").asDouble();

            String routeUrl = String.format(Locale.US,
                    "https://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=full&geometries=geojson",
                    lon1, lat1, lon2, lat2);

            ResponseEntity<String> routeResponse = restTemplate.getForEntity(routeUrl, String.class);

            HttpHeaders cleanHeaders = new HttpHeaders();
            cleanHeaders.set("Content-Type", "application/json");

            return new ResponseEntity<>(routeResponse.getBody(), cleanHeaders, routeResponse.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("{\"error\": \"Erro ao calcular rota.\"}");
        }
    }
}
