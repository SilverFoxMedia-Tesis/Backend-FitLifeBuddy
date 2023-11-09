package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitLifeBuddy.Entity.Enum.TypeHealthCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "healthCondition")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCondition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHealthCondition;
    @Column(name = "NameHealthCondition", nullable = false,length = 20)
    private String nameHealthCondition;
    @Column(name = "DescriptionHealthCondition", nullable = false,length = 50)
    private String descriptionHealthCondition;
    @Enumerated(EnumType.STRING)
    @Column(name = "TypeHealthCondition", nullable = false, length = 20)
    private TypeHealthCondition typeHealthCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPacient", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Pacient pacient;

}
