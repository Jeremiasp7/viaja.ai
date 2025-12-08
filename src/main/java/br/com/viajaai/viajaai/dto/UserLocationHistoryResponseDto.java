package br.com.viajaai.viajaai.dto;

import br.com.viajaai.viajaai.entities.UserLocationHistory;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocationHistoryResponseDto {

  private UUID id;
  private String city;
  private String country;
  private boolean isFavorite;
  private boolean hasVisited;
  private LocalDate lastVisitedDate;
  private UUID userId;

  public static UserLocationHistoryResponseDto fromEntity(UserLocationHistory entity) {
    UserLocationHistoryResponseDto dto = new UserLocationHistoryResponseDto();
    dto.setId(entity.getId());
    dto.setCity(entity.getCity());
    dto.setCountry(entity.getCountry());
    dto.setFavorite(entity.isFavorite());
    dto.setHasVisited(entity.isHasVisited());
    dto.setLastVisitedDate(entity.getLastVisitedDate());

    if (entity.getUser() != null) {
      dto.setUserId(entity.getUser().getId());
    }
    return dto;
  }
}
