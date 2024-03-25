package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mealFood")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealFood implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMealFood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMeal",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFood",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Food food;

}
