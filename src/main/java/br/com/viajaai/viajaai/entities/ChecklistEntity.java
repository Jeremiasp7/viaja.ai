package br.com.viajaai.viajaai.entities;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "checklists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String nome;
  private boolean concluido;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "travel_plan_id")
  private TravelPlanEntity travelPlan;
}
