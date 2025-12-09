package br.com.planejaai.framework.entity;

import java.time.LocalDateTime; // Ou LocalDate, dependendo da precisão desejada
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "scheduled_events_base")
public abstract class ScheduledEventEntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Antes: country -> Agora: O nome principal do evento (ex: "Treino de Pernas", "França")
    @Column(nullable = false)
    private String title;

    // Antes: city -> Agora: Local ou subtítulo (ex: "SmartFit", "Paris")
    private String location; 
    
    // Antes: city -> Agora: Descrição opcional (ex: "Foco em quadríceps")
    private String description;

    // Antes: arrivalDate
    @Column(name = "start_date")
    private LocalDateTime startDate;

    // Antes: departureDate
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    @JsonIgnore
    private GenericPlanEntityAbstract plan;
}