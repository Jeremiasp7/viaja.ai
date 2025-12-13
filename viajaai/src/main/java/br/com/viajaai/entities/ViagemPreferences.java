package br.com.viajaai.entities;

import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "viagem_preferences")
public class ViagemPreferences extends UserPreferencesEntityAbstract {

    private String assentoPreferencia; 
    
    private String restricoesAlimentares;
    
    private boolean aceitaEscalas;
}