package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "routine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoutine;
    @Column(name = "NameRoutine", nullable = false, length = 20)
    private String nameRoutine;
    @Column(name = "DescriptionRoutine", nullable = false, length = 100)
    private String descriptionRoutine;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDaily", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Daily daily;

    @JsonIgnore
    @OneToMany(mappedBy = "routine", fetch = FetchType.LAZY)
    private Set<RoutineExercise> routineExercises = new HashSet<>();
}
