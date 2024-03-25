package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitLifeBuddy.Entity.Enum.BodyPart;
import com.fitLifeBuddy.Entity.Enum.TypeExercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exercise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExercise;
    @Column(name = "Workout", nullable = false, length = 20)
    private String workout;
    @Column(name = "Sets", nullable = false, length = 5)
    private String sets;
    @Column(name = "RepsPerSet", nullable = false, length = 5)
    private String repsPerSet;

    @Enumerated(EnumType.STRING)
    @Column(name = "TypeExercise", nullable = false, length = 20)
    private TypeExercise typeExercise;
    @Enumerated(EnumType.STRING)
    @Column(name = "BodyPart", nullable = false, length = 20)
    private BodyPart bodyPart;

    @JsonIgnore
    @OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY)
    private Set<RoutineExercise> routineExercises = new HashSet<>();
}
