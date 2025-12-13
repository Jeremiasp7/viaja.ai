package br.com.viajaai.entities;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "viagem_plans")
public class ViagemPlan extends GenericPlanEntityAbstract {

    private String destino;
    
    private String estiloViagem;
    

    private Integer numeroPessoas;
}