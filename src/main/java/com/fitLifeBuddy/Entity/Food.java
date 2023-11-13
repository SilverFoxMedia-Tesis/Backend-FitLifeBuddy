package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.FoodOrigin;
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
    @Column(name = "Proteins_100g", nullable = false)
    private Float proteins_100g;
    @Column(name = "Carbohydrates_100g", nullable = false)
    private Float carbohydrates_100g;
    @Column(name = "Fat_100g", nullable = false)
    private Float fat_100g;
    @Column(name = "Energy_100g", nullable = false)
    private Float energy_100g;
    @Column(name = "CategoryName", nullable = false, length = 20)
    private String categoryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "FoodOrigin", nullable = false, length = 10)
    private FoodOrigin foodOrigin;
    @Enumerated(EnumType.STRING)
    @Column(name = "DietType", nullable = false, length = 15)
    private DietType dietType;

    @JsonIgnore
    @ManyToMany(mappedBy = "foods")
    private Set<Meal> meals = new HashSet<>();

}
