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
    private Integer idUser;
    @Column(name = "Fullname", nullable = false, length = 50)
    private String fullname;
    @Column(name = "Lastname", nullable = false, length = 50)
    private String lastname;
    @Column(name = "Created", nullable = true)
    private String created;
    @Column(name = "Updated", nullable = true)
    private String updated;
    @Column(name = "Deleted", nullable = true)
    private String deleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAuth", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Auth auth;
}
