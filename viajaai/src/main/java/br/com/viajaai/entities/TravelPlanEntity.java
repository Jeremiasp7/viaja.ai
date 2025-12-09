package br.com.viajaai.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "travel_plans")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelPlanEntity extends GenericPlanEntityAbstract {
    
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;


    @OneToMany(
        mappedBy = "plan",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private List<DestinationEntity> destinations;

    @OneToMany(
        mappedBy = "plan",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private List<DayItineraryEntity> dayItinerary;

    @OneToOne(mappedBy = "plan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BudgetEntity budget;
}