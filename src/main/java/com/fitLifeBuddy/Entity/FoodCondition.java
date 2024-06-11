package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitLifeBuddy.Entity.Enum.TypeFoodCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "foodCondition")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodCondition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFoodCondition;
    @Column(name = "NameFoodCondition", nullable = false,length = 50)
    private String nameFoodCondition;
    @Column(name = "DescriptionFoodCondition", nullable = false,length = 50)
    private String descriptionFoodCondition;
    @Enumerated(EnumType.STRING)
    @Column(name = "TypeFoodCondition", nullable = false, length = 20)
    private TypeFoodCondition typeFoodCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPacient", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Pacient pacient;

}
