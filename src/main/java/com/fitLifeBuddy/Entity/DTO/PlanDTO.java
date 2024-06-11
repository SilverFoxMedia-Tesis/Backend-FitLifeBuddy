package com.fitLifeBuddy.Entity.DTO;

import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Enum.GoalType;

public class PlanDTO {
    private Frecuently frecuently;
    private GoalType goalType;

    // Getters y setters
    public Frecuently getFrecuently() {
        return frecuently;
    }

    public void setFrecuently(Frecuently frecuently) {
        this.frecuently = frecuently;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }
}
