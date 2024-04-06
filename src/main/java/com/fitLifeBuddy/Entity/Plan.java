package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;
    @Enumerated(EnumType.STRING)
    @Column(name = "Frecuently", nullable = false, length = 20)
    private Frecuently frecuently;
    @Enumerated(EnumType.STRING)
    @Column(name = "DietType", nullable = false, length = 20)
    private DietType dietType;
    @Column(name = "TMB", nullable = false)
    private Float tmb;
    @Column(name = "CalorieDeficit", nullable = false)
    private Float calorieDeficit;
    @Column(name = "Carbohydrates_g", nullable = false)
    private Float carbohydrates_g;
    @Column(name = "Proteins_g", nullable = false)
    private Float proteins_g;
    @Column(name = "Fats_g", nullable = false)
    private Float fats_g;
    @Column(name = "WeightLose", nullable = false)
    private Float weightLose;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 10)
    private Status status = Status.ACTIVED;
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = Status.ACTIVED;
        }
    }

    @JsonIgnore
    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    private Set<Daily> dailies = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPacient", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Pacient pacient;

}
