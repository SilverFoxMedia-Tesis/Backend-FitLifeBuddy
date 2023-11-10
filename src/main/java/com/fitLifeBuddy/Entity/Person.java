package com.fitLifeBuddy.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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



}
