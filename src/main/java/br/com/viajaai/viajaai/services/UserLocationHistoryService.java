package br.com.viajaai.viajaai.services;

import br.com.viajaai.viajaai.dto.CreateUserLocationHistoryDto;
import br.com.viajaai.viajaai.dto.UserLocationHistoryResponseDto;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.entities.UserLocationHistory;
import br.com.viajaai.viajaai.repositories.UserLocationHistoryRepository;
import br.com.viajaai.viajaai.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserLocationHistoryService {

    private final UserLocationHistoryRepository historyRepository;
    private final UserRepository userRepository;
    public UserLocationHistoryResponseDto addLocationHistory(UUID userId, CreateUserLocationHistoryDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        UserLocationHistory newHistory = dto.toEntity();
        
        newHistory.setUser(user);

        UserLocationHistory savedHistory = historyRepository.save(newHistory);
        return UserLocationHistoryResponseDto.fromEntity(savedHistory);
    }

    public List<UserLocationHistoryResponseDto> getAllHistoryForUser(UUID userId) {
        return historyRepository.findByUserId(userId)
                .stream()
                .map(UserLocationHistoryResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserLocationHistoryResponseDto> getFavoriteHistoryForUser(UUID userId) {
        return historyRepository.findByUserIdAndIsFavoriteTrue(userId)
                .stream()
                .map(UserLocationHistoryResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserLocationHistoryResponseDto> getVisitedHistoryForUser(UUID userId) {
        return historyRepository.findByUserIdAndHasVisitedTrue(userId)
                .stream()
                .map(UserLocationHistoryResponseDto::fromEntity) // Use Dto static method
                .collect(Collectors.toList());
    }

    public UserLocationHistoryResponseDto updateLocationHistory(UUID historyId, CreateUserLocationHistoryDto dto) {
        UserLocationHistory existingHistory = historyRepository.findById(historyId)
                .orElseThrow(() -> new EntityNotFoundException("LocationHistory not found with ID: " + historyId));

        dto.updateEntity(existingHistory);

        UserLocationHistory updatedHistory = historyRepository.save(existingHistory);
        return UserLocationHistoryResponseDto.fromEntity(updatedHistory);
    }

    public void deleteLocationHistory(UUID historyId) {
        if (!historyRepository.existsById(historyId)) {
            throw new EntityNotFoundException("LocationHistory not found with ID: " + historyId);
        }
        historyRepository.deleteById(historyId);
    }
}
