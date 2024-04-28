package com.fitLifeBuddy.Entity.DTO;

import java.util.Date;

public class DailyDTO {
    private Date date;
    private Integer dateNumber;

    public DailyDTO() {
    }

    public DailyDTO(Date date, Integer dateNumber) {
        this.date = date;
        this.dateNumber = dateNumber;
    }

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
}
