package br.com.viajaai.entities; // Confirme se o pacote é EXATAMENTE este

import java.time.LocalDate;
import java.util.List;

import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "travel_preferences")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesEntity extends UserPreferencesEntityAbstract {
    private String estiloDeViagem;
    private String preferenciaDeAcomodacao;
    private String preferenciaDeClima;

    private String faixaOrcamentaria;
    private Integer duracaoDaViagem;

    private String companhiaDeViagem;
    
    @ElementCollection
    @Column(name = "data_preferida")
    private List<LocalDate> preferenciaDeDatas;
}