package com.fitLifeBuddy.Entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "Pacient Data Transfer Object")
public class PacientDTO {
    @ApiModelProperty(notes = "Birthdate of the pacient")
    private Date birthDate;

    // Constructor, Getters y Setters

    public PacientDTO() {
        // Constructor por defecto
    }

    public PacientDTO(Date birthDate) {
        this.birthDate = birthDate;
    }

    // Getter y Setter para birthDate
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
