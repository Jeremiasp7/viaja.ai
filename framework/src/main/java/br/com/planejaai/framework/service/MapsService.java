package br.com.planejaai.framework.service;

import java.util.Locale;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MapsService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public ResponseEntity<String> geocode(String address) {
        String url = "https://nominatim.openstreetmap.org/search?q="
                     + address + "&format=json&limit=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "viajaai");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> reverse(double lat, double lon) {
        String url = "https://nominatim.openstreetmap.org/reverse?lat=" 
                     + lat + "&lon=" + lon + "&format=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "viajaai");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getRouteByAddress(String origin, String destination) {
        try {
            // --- Geocode da origem ---
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

            var destResponse = geocode(destination);
            var destArray = mapper.readTree(destResponse.getBody());

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
