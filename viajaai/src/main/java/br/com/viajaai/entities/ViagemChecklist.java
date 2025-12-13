package br.com.viajaai.entities;

import br.com.planejaai.framework.entity.ChecklistEntityAbstract;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "viagem_checklists")
public class ViagemChecklist extends ChecklistEntityAbstract {

    private String categoria;
    
    private Integer quantidade;
}