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
@Table(name = "meal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMeal;
    @Column(name = "NameMeal", nullable = false, length = 20)
    private String nameMeal;
    @Column(name = "DescriptionMeal", nullable = false, length = 100)
    private String descriptionMeal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDaily", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Daily daily;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "mealFood",
            joinColumns = @JoinColumn(name = "idMeal"),
            inverseJoinColumns = @JoinColumn(name = "idFood")
    )
    private Set<Food> foods = new HashSet<>();

}
