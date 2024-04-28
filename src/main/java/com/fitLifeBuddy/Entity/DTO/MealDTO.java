package com.fitLifeBuddy.Entity.DTO;

import com.fitLifeBuddy.Entity.Enum.TimeMeal;

public class MealDTO {
    private TimeMeal timeMeal;

    public MealDTO() {
    }

    public MealDTO(TimeMeal timeMeal) {
        this.timeMeal = timeMeal;
    }

    public TimeMeal getTimeMeal() {
        return timeMeal;
    }

    public void setTimeMeal(TimeMeal timeMeal) {
        this.timeMeal = timeMeal;
    }
}
