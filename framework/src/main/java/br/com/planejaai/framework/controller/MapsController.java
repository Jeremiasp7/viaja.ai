package br.com.planejaai.framework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.planejaai.framework.service.MapsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/maps")
@RequiredArgsConstructor
public class MapsController {

    private final MapsService mapsService;

    @GetMapping("/geocode")
    public ResponseEntity<String> geocode(@RequestParam String address) {
        return mapsService.geocode(address);
    }

    @GetMapping("/reverse")
    public ResponseEntity<String> reverse(@RequestParam double lat, @RequestParam double lon) {
        return mapsService.reverse(lat, lon);
    }

    @GetMapping("/route")
    public ResponseEntity<String> getRouteByAddress(
            @RequestParam String origin,
            @RequestParam String destination) {
        return mapsService.getRouteByAddress(origin, destination);
    }
}

