package com.fitLifeBuddy.Entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDaily", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Daily daily;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "routineExercise",
            joinColumns = @JoinColumn(name = "idRoutine"),
            inverseJoinColumns = @JoinColumn(name = "idExercise")
    )
    private Set<Exercise> exercises = new HashSet<>();
}
