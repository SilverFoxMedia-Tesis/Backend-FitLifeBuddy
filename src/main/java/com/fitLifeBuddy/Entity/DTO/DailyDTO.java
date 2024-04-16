package com.fitLifeBuddy.Entity.DTO;

import com.fitLifeBuddy.Entity.Enum.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "Data Transfer Object for Daily entity excluding ID and relationships")
public class DailyDTO {

    @ApiModelProperty(required = true, notes = "The date for the daily entry")
    private Date date;

    @ApiModelProperty(required = true, notes = "Numerical identifier for the date")
    private Integer dateNumber;

    @ApiModelProperty(required = true, notes = "Status of the daily entry")
    private Status status;

    // Getters y setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDateNumber() {
        return dateNumber;
    }

    public void setDateNumber(Integer dateNumber) {
        this.dateNumber = dateNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Constructors
    public DailyDTO() {
    }

    public DailyDTO(Date date, Integer dateNumber, Status status) {
        this.date = date;
        this.dateNumber = dateNumber;
        this.status = status;
    }

    // Additional methods or logic as needed
}
