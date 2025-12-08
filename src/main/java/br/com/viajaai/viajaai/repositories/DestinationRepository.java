package br.com.viajaai.viajaai.repositories;

import br.com.viajaai.viajaai.entities.DestinationEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationEntity, UUID> {

  @Query("""
	SELECT d
	FROM destinations d
	JOIN d.travelPlan tp
	WHERE tp.user.id = :userId
	""")
  public List<DestinationEntity> findByUserId(UUID userId);
}
