package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitLifeBuddy.Entity.Enum.BodyPart;
import com.fitLifeBuddy.Entity.Enum.Equipment;
import com.fitLifeBuddy.Entity.Enum.Level;
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
    @Column(name = "Title", nullable = false, length = 20)
    private String title;
    @Column(name = "DescriptionExercise", nullable = false, length = 50)
    private String descriptionExercise;
    @Enumerated(EnumType.STRING)
    @Column(name = "TypeExercise", nullable = false, length = 20)
    private TypeExercise typeExercise;
    @Enumerated(EnumType.STRING)
    @Column(name = "BodyPart", nullable = false, length = 20)
    private BodyPart bodyPart;
    @Enumerated(EnumType.STRING)
    @Column(name = "Equipment", nullable = false, length = 20)
    private Equipment equipment;
    @Enumerated(EnumType.STRING)
    @Column(name = "Level", nullable = false, length = 20)
    private Level level;
    @Column(name = "Rating", nullable = true)
    private Float rating;
    @Column(name = "RatingDesc",  nullable = true)
    private Float ratingDesc;

    @JsonIgnore
    @ManyToMany(mappedBy = "exercises")
    private Set<Routine> routines = new HashSet<>();
}
