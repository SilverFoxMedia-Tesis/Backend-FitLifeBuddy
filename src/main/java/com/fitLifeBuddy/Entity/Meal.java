package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitLifeBuddy.Entity.Enum.TimeMeal;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "TimeMeal", nullable = false, length = 10)
    private TimeMeal timeMeal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDaily", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Daily daily;

    @JsonIgnore
    @OneToMany(mappedBy = "meal", fetch = FetchType.LAZY)
    private Set<MealFood> mealFoods = new HashSet<>();

}
