package com.fitLifeBuddy.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Auth.findByEmailAddress",query = "select a from Auth a where a.emailAddress=?1")
public class Auth implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAuth;
    @Column(name = "EmailAddress", nullable = false, length = 30)
    private String emailAddress;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "Token", nullable = false)
    private String token;
    @Column(name = "TypeHash", nullable = false)
    private String typeHash;

}
