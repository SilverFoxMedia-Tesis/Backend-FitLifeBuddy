package com.fitLifeBuddy.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Client.findByName",query = "select b from Client b where b.name=?1")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="TypeDocument", nullable = false, length = 20)
    private String typeDocument;
    @Column(name="NumberId", nullable = false, unique = true, length = 20)
    private Long numberId;
    @Column(name="LastName", nullable = false, length = 50)
    private String lastName;
    @Column(name="Name", nullable = false, length = 50)
    private String name;
    @Column(name="Address", nullable = false, length = 50)
    private String address;
    @Column(name="Reference", nullable = false, length = 50)
    private String reference;
    @Column(name="PhoneNumber", nullable = false, length = 50)
    private Long phoneNumber;
    @Column(name="EmailAddress", nullable = false, length = 50)
    private String emailAddress;
    @Column(name="Issue", nullable = false, length = 50)
    private String issue;
}