package br.com.viajaai.repositories;
import br.com.viajaai.entities.ViagemPlan;
import br.com.planejaai.framework.repository.GenericPlanRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ViagemPlanRepository extends GenericPlanRepository<ViagemPlan> {
    

}