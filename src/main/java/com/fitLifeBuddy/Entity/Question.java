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
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuestion;
    @Column(name = "NameQuestion",nullable = false, length = 50)
    private String nameQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFeedback", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Feedback feedback;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private Set<Option> options = new HashSet<>();
}
