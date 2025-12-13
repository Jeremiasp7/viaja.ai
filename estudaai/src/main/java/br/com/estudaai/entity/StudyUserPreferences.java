package br.com.estudaai.entity;

import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "study_preferences")
public class StudyUserPreferences extends UserPreferencesEntityAbstract {

    @Enumerated(EnumType.STRING)
    private LearningStyle learningStyle;

    private boolean usePomodoro;

    @Column
    private Integer focusSessionMinutes = 50;

    private Integer breakMinutes = 25;

    private Integer weekdayMinutesAvailable; 
    private Integer weekendMinutesAvailable;
}
