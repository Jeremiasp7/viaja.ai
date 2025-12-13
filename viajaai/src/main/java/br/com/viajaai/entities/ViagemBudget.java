package br.com.viajaai.entities;

import br.com.planejaai.framework.entity.ResourcesEntityAbstract;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "viagem_budgets")
public class ViagemBudget extends ResourcesEntityAbstract {


    private Double valorGasto;
}