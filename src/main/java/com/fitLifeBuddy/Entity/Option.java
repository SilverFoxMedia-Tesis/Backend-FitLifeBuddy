package com.fitLifeBuddy.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "option")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOption;
    @Column(name = "NameOption", nullable = false, length = 20)
    private String nameOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idQuestion", nullable = true)
    private Question question;

}
