package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "daily")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Daily implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDaily;
    @Column(name = "Date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "DateNumber", nullable = false)
    private Integer dateNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "daily", fetch = FetchType.LAZY)
    private Set<Meal> meals = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "daily",fetch = FetchType.LAZY)
    private Set<Routine> routines = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlan", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Plan plan;

}
