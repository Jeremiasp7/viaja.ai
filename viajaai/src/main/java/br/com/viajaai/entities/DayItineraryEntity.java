package br.com.viajaai.entities;

import br.com.planejaai.framework.entity.DayItineraryEntityAbstract;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "day_itinerary")
@NoArgsConstructor
public class DayItineraryEntity extends DayItineraryEntityAbstract {

}