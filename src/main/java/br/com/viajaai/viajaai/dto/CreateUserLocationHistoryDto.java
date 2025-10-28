package br.com.viajaai.viajaai.dto;

import br.com.viajaai.viajaai.entities.UserLocationHistory;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserLocationHistoryDto {
    
    private String city;
    private String country;
    private boolean isFavorite = false;
    private boolean hasVisited = false;
    private LocalDate lastVisitedDate;

    public UserLocationHistory toEntity() {
        UserLocationHistory entity = new UserLocationHistory();
        entity.setCity(this.city);
        entity.setCountry(this.country);
        entity.setFavorite(this.isFavorite);
        entity.setHasVisited(this.hasVisited);
        entity.setLastVisitedDate(this.lastVisitedDate);
        return entity;
    }

    public void updateEntity(UserLocationHistory entity) {
        entity.setCity(this.city);
        entity.setCountry(this.country);
        entity.setFavorite(this.isFavorite);
        entity.setHasVisited(this.hasVisited);
        entity.setLastVisitedDate(this.lastVisitedDate);
    }
}
