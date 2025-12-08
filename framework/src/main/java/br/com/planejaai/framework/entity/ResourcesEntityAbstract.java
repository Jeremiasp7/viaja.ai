package br.com.planejaai.framework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resources")
public abstract class ResourcesEntityAbstract { // BudgetEntity - Viajaai

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Double
      mainResource; // totalAmount - Viajaai, tempo - Estudaai, tempo de treino - Treinaai
  private String resourceType; // currency - Viajaai,

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "travel_plan_id")
  @JsonIgnore
  private GenericPlanEntityAbstract travelPlan;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private UserEntity user;
}
