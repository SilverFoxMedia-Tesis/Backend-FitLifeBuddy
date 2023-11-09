package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "User.findByFullname",query = "select u from User u where u.fullname=?1")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @Column(name = "Fullname", nullable = false, length = 50)
    private String fullname;
    @Column(name = "Lastname", nullable = false, length = 50)
    private String lastname;
    @Column(name = "EmailAddress", nullable = false, length = 100)
    private String emailAddress;
    @Column(name = "Password", nullable = false, length = 50)
    private String password;
    @Column(name = "Created", nullable = true)
    private String created;
    @Column(name = "Updated", nullable = true)
    private String updated;
    @Column(name = "Deleted", nullable = true)
    private String deleted;

}
