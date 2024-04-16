package com.fitLifeBuddy.Entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Data Transfer Object for Person entity excluding ID")
public class PersonDTO {

    @ApiModelProperty(required = true, notes = "Full name of the person")
    private String fullname;

    @ApiModelProperty(required = true, notes = "Last name of the person")
    private String lastname;

    @ApiModelProperty(required = true, notes = "Email address of the person")
    private String emailAddress;

    @ApiModelProperty(required = true, notes = "Password of the person")
    private String password;

    // Getters y Setters
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Constructor vacío
    public PersonDTO() {
    }

    // Constructor con todos los campos
    public PersonDTO(String fullname, String lastname, String emailAddress, String password) {
        this.fullname = fullname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    // Métodos de conveniencia o lógica adicional si es necesario
}
