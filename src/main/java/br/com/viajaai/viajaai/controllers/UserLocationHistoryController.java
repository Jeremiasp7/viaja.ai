package br.com.viajaai.viajaai.controllers;

import br.com.viajaai.viajaai.dto.CreateUserLocationHistoryDto;
import br.com.viajaai.viajaai.dto.UserLocationHistoryResponseDto;
import br.com.viajaai.viajaai.services.UserLocationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class UserLocationHistoryController {

    private final UserLocationHistoryService historyService;

    @PostMapping("/users/{userId}/history")
    public ResponseEntity<UserLocationHistoryResponseDto> addHistoryToUser(
            @PathVariable UUID userId, 
            @RequestBody CreateUserLocationHistoryDto locationHistoryDTO) {
        
        UserLocationHistoryResponseDto savedHistory = historyService.addLocationHistory(userId, locationHistoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHistory);
    }

    @GetMapping("/users/{userId}/history")
    public ResponseEntity<List<UserLocationHistoryResponseDto>> getAllHistoryForUser(@PathVariable UUID userId) {
        List<UserLocationHistoryResponseDto> history = historyService.getAllHistoryForUser(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/users/{userId}/history/favorites")
    public ResponseEntity<List<UserLocationHistoryResponseDto>> getFavoriteHistoryForUser(@PathVariable UUID userId) {
        List<UserLocationHistoryResponseDto> favorites = historyService.getFavoriteHistoryForUser(userId);
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/users/{userId}/history/visited")
    public ResponseEntity<List<UserLocationHistoryResponseDto>> getVisitedHistoryForUser(@PathVariable UUID userId) {
        List<UserLocationHistoryResponseDto> visited = historyService.getVisitedHistoryForUser(userId);
        return ResponseEntity.ok(visited);
    }

    @PutMapping("/history/{historyId}")
    public ResponseEntity<UserLocationHistoryResponseDto> updateHistory(
            @PathVariable UUID historyId, 
            @RequestBody CreateUserLocationHistoryDto locationHistoryDTO) {
        
        UserLocationHistoryResponseDto updatedHistory = historyService.updateLocationHistory(historyId, locationHistoryDTO);
        return ResponseEntity.ok(updatedHistory);
    }

    @DeleteMapping("/history/{historyId}")
    public ResponseEntity<Void> deleteHistory(@PathVariable UUID historyId) {
        historyService.deleteLocationHistory(historyId);
        return ResponseEntity.noContent().build();
    }
}
