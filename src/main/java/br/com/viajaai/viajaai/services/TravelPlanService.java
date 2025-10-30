package br.com.viajaai.viajaai.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.CreateTravelPlanDto;
import br.com.viajaai.viajaai.dto.DayItineraryResponseDto;
import br.com.viajaai.viajaai.dto.TravelPlanResponseDto;
import br.com.viajaai.viajaai.dto.DestinationResponseDto;
import br.com.viajaai.viajaai.entities.DayItineraryEntity;
import br.com.viajaai.viajaai.entities.DestinationEntity;
import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.exceptions.TravelPlanNaoEncontradoException;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.repositories.TravelPlanRepository;
import br.com.viajaai.viajaai.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;

    @Transactional
    public TravelPlanResponseDto createTravelPlan(CreateTravelPlanDto dto) throws UsuarioNaoEncontradoException {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + dto.getUserId()));

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
                    .arrivalDate(destDto.getArrivalDate())
                    .departureDate(destDto.getDepartureDate())
                    .travelPlan(travelPlan)
                    .build();
        }).collect(Collectors.toList());

        List<DayItineraryEntity> dayItinerary = dto.getDayItinerary().stream().map(itineraryDto -> {
            return DayItineraryEntity.builder()
                    .title(itineraryDto.getTitle())
                    .activities(itineraryDto.getActivities())
                    .travelPlan(travelPlan)
                    .build();  
        }).collect(Collectors.toList());

        travelPlan.setDayItinerary(dayItinerary);
        travelPlan.setDestinations(destinations);
        TravelPlanEntity savedPlan = travelPlanRepository.save(travelPlan);

        return toResponseDto(savedPlan);
    }

    public List<TravelPlanResponseDto> getTravelPlansByUserId(UUID userId) throws UsuarioNaoEncontradoException {
        if (!userRepository.existsById(userId)) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + userId);
        }

        return travelPlanRepository.findByUserId(userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public TravelPlanResponseDto getTravelPlanByIdDto(UUID id) {
        TravelPlanEntity plan = travelPlanRepository.findById(id)
                .orElseThrow(() -> new TravelPlanNaoEncontradoException("Plano de viagem não encontrado com o ID: " + id));
        return toResponseDto(plan);
    }

    @Transactional
    public TravelPlanResponseDto updateTravelPlan(UUID id, CreateTravelPlanDto dto) {
        TravelPlanEntity existingPlan = travelPlanRepository.findById(id)
                .orElseThrow(() -> new TravelPlanNaoEncontradoException("Plano de viagem não encontrado com o ID: " + id));

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

        existingPlan.getDayItinerary().clear();

        List<DayItineraryEntity> updateDayItinerary = dto.getDayItinerary().stream().map(itineraryDto ->
            DayItineraryEntity.builder()
                .title(itineraryDto.getTitle())
                .activities(itineraryDto.getActivities())
                .travelPlan(existingPlan)
                .build()
        ).collect(Collectors.toList());

        existingPlan.getDayItinerary().addAll(updateDayItinerary);

        TravelPlanEntity savedPlan = travelPlanRepository.save(existingPlan);
        return toResponseDto(savedPlan);
    }

    public void deleteTravelPlan(UUID id) {
        if (!travelPlanRepository.existsById(id)) {
            throw new TravelPlanNaoEncontradoException("Plano de viagem não encontrado com o ID: " + id);
        }
        travelPlanRepository.deleteById(id);
    }

    private TravelPlanResponseDto toResponseDto(TravelPlanEntity entity) {
        List<DestinationResponseDto> destinations = entity.getDestinations().stream()
                .map(dest -> new DestinationResponseDto(
                        dest.getCountry(),
                        dest.getCity(),
                        dest.getArrivalDate(),
                        dest.getDepartureDate()))
                .collect(Collectors.toList());
        
        List<DayItineraryResponseDto> dayItinerary = entity.getDayItinerary().stream()
                .map(itinerary -> new DayItineraryResponseDto(
                        itinerary.getTitle(),
                        itinerary.getActivities()))
                .collect(Collectors.toList());

        return new TravelPlanResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getStartDate(),
                entity.getEndDate(),
                destinations, dayItinerary);
    }
}
