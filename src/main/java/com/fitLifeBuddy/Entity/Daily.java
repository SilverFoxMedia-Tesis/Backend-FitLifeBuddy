package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitLifeBuddy.Entity.Enum.Status;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 10)
    private Status status = Status.UNFILLED;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = Status.UNFILLED;
        }
    }

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
