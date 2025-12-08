package br.com.viajaai.viajaai.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "destinations")
@Builder
public class DestinationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String country;
  private String city;
  private LocalDate arrivalDate;
  private LocalDate departureDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "travel_plan_id")
  @JsonBackReference // Evita a serialização infinita
  private TravelPlanEntity travelPlan;
}
