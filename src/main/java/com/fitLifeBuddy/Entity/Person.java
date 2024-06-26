package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@NamedQuery(name = "Person.findByFullname",query = "select u from Person u where u.fullname=?1")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;
    @Column(name = "Fullname", nullable = false, length = 50)
    private String fullname;
    @Column(name = "Lastname", nullable = false, length = 50)
    private String lastname;
    @Column(name = "EmailAddress", nullable = false, length = 100)
    private String emailAddress;
    @Column(name = "Password", nullable = false, length = 50)
    private String password;
    @Column(name = "AcceptsNotifications", nullable = false)
    private boolean acceptsNotifications = false;

    @JsonIgnore
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Notification> notifications = new HashSet<>();

}
