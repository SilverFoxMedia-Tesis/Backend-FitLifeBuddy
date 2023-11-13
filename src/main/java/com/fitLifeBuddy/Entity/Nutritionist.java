package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nutritionist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nutritionist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNutritionist;

    @JsonIgnore
    @OneToMany(mappedBy = "nutritionist", fetch = FetchType.LAZY)
    private Set<Pacient> pacients = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPerson", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Person person;
}
