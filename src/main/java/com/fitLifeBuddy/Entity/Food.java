package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitLifeBuddy.Entity.Enum.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "food")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFood;
    @Column(name = "NameFood", nullable = false, length = 20)
    private String nameFood;
    @Column(name = "Measure", nullable = false,  length = 10)
    private String measure;
    @Column(name = "Grams", nullable = false)
    private Float grams;
    @Column(name = "Calories", nullable = false)
    private Float calories;
    @Column(name = "Fat", nullable = false)
    private Float fat;
    @Column(name = "SatFat", nullable = false)
    private Float satFat;
    @Column(name = "Fiber", nullable = false)
    private Float fiber;
    @Column(name = "Carbs", nullable = false)
    private Float carbs;

    @Enumerated(EnumType.STRING)
    @Column(name = "CategoryName", nullable = false, length = 35)
    private CategoryName categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    private Set<MealFood> mealFoods = new HashSet<>();

}
