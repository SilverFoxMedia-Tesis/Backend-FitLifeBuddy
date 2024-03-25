package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "routineExercise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineExercise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoutineExercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRoutine", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idExercise", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Exercise exercise;

}
