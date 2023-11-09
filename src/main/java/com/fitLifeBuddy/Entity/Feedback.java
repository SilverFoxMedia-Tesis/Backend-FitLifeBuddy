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
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFeedback;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlan", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Plan plan;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDaily", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Daily daily;

    @OneToMany(mappedBy = "feedback", fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();
}
