package br.com.viajaai.viajaai.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import br.com.viajaai.viajaai.dto.CreateTravelPlanDto;
import br.com.viajaai.viajaai.entities.DestinationEntity;
import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.repositories.TravelPlanRepository;
import br.com.viajaai.viajaai.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;

    @Transactional
    public TravelPlanEntity createTravelPlan(CreateTravelPlanDto dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + dto.getUserId()));

        TravelPlanEntity travelPlan = TravelPlanEntity.builder()
                .title(dto.getTitle())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .user(user)
                .build();
        
        List<DestinationEntity> destinations = dto.getDestinations().stream().map(destDto -> {
            return DestinationEntity.builder()
                    .country(destDto.getCountry())
                    .city(destDto.getCity())
                    .arrivalDate(destDto.getArrivalDate()) // O usuário vai ter que fornecer a data de chegada duas vezes?
                    .departureDate(destDto.getDepartureDate())
                    .travelPlan(travelPlan) // Associa o destino ao plano
                    .build();
        }).collect(Collectors.toList());

        travelPlan.setDestinations(destinations);

        return travelPlanRepository.save(travelPlan);
    }

    public List<TravelPlanEntity> getTravelPlansByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
             throw new EntityNotFoundException("Usuário não encontrado com o ID: " + userId);
        }
        return travelPlanRepository.findByUserId(userId);
    }

    public TravelPlanEntity getTravelPlanById(UUID id) {
        return travelPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano de viagem não encontrado com o ID: " + id));
    }

    @Transactional
    public TravelPlanEntity updateTravelPlan(UUID id, CreateTravelPlanDto dto) {
        TravelPlanEntity existingPlan = getTravelPlanById(id);

        existingPlan.setTitle(dto.getTitle());
        existingPlan.setStartDate(dto.getStartDate());
        existingPlan.setEndDate(dto.getEndDate());

        existingPlan.getDestinations().clear();

        List<DestinationEntity> updatedDestinations = dto.getDestinations().stream().map(destDto -> 
            DestinationEntity.builder()
                .country(destDto.getCountry())
                .city(destDto.getCity())
                .arrivalDate(destDto.getArrivalDate())
                .departureDate(destDto.getDepartureDate())
                .travelPlan(existingPlan)
                .build()
        ).collect(Collectors.toList());
        
        existingPlan.getDestinations().addAll(updatedDestinations);

        return travelPlanRepository.save(existingPlan);
    }

    public void deleteTravelPlan(UUID id) {
        if (!travelPlanRepository.existsById(id)) {
            throw new EntityNotFoundException("Plano de viagem não encontrado com o ID: " + id);
        }
        travelPlanRepository.deleteById(id);
    }
}
