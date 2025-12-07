package br.com.viajaai.viajaai.repositories;

import br.com.viajaai.viajaai.entities.UserLocationHistory;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationHistoryRepository extends JpaRepository<UserLocationHistory, UUID> {

  List<UserLocationHistory> findByUserId(UUID userId);

  List<UserLocationHistory> findByUserIdAndIsFavoriteTrue(UUID userId);

  List<UserLocationHistory> findByUserIdAndHasVisitedTrue(UUID userId);
}
