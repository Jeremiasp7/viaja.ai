package br.com.viajaai.entities;

import java.time.LocalDate;

import br.com.planejaai.framework.entity.ScheduledEventEntityAbstract;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "destinations") // Cria a tabela física 'destinations'
@NoArgsConstructor
public class DestinationEntity extends ScheduledEventEntityAbstract {
    @Transient
    public String getCountry() {
        return this.getTitle();
    }

    public void setCountry(String country) {
        this.setTitle(country);
    }

    @Transient
    public String getCity() {
        return this.getLocation();
    }

    public void setCity(String city) {
        this.setLocation(city);
    }

    @Transient
    public LocalDate getArrivalDate() {
        return this.getStartDate() != null ? this.getStartDate().toLocalDate() : null;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        if (arrivalDate != null) {
            this.setStartDate(arrivalDate.atStartOfDay());
        }
    }

    @Transient
    public LocalDate getDepartureDate() {
        return this.getEndDate() != null ? this.getEndDate().toLocalDate() : null;
    }

    public void setDepartureDate(LocalDate departureDate) {
        if (departureDate != null) {
            this.setEndDate(departureDate.atStartOfDay());
        }
    }
}