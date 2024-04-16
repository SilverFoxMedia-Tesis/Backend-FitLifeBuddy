package com.fitLifeBuddy.Entity.DTO;

import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Enum.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Data Transfer Object for Plan entity excluding ID and relationships")
public class PlanDTO {

    @ApiModelProperty(required = true, notes = "Frequency of the diet plan")
    private Frecuently frecuently;

    @ApiModelProperty(required = true, notes = "Type of diet")
    private DietType dietType;

    @ApiModelProperty(required = true, notes = "Total Metabolic Rate")
    private Float tmb;

    @ApiModelProperty(required = true, notes = "Caloric deficit")
    private Float calorieDeficit;

    @ApiModelProperty(required = true, notes = "Grams of carbohydrates")
    private Float carbohydrates_g;

    @ApiModelProperty(required = true, notes = "Grams of proteins")
    private Float proteins_g;

    @ApiModelProperty(required = true, notes = "Grams of fats")
    private Float fats_g;

    @ApiModelProperty(required = true, notes = "Expected weight loss")
    private Float weightLose;

    @ApiModelProperty(required = true, notes = "Status of the plan")
    private Status status;

    public Frecuently getFrecuently() {
        return frecuently;
    }

    public void setFrecuently(Frecuently frecuently) {
        this.frecuently = frecuently;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public Float getTmb() {
        return tmb;
    }

    public void setTmb(Float tmb) {
        this.tmb = tmb;
    }

    public Float getCalorieDeficit() {
        return calorieDeficit;
    }

    public void setCalorieDeficit(Float calorieDeficit) {
        this.calorieDeficit = calorieDeficit;
    }

    public Float getCarbohydrates_g() {
        return carbohydrates_g;
    }

    public void setCarbohydrates_g(Float carbohydrates_g) {
        this.carbohydrates_g = carbohydrates_g;
    }

    public Float getProteins_g() {
        return proteins_g;
    }

    public void setProteins_g(Float proteins_g) {
        this.proteins_g = proteins_g;
    }

    public Float getFats_g() {
        return fats_g;
    }

    public void setFats_g(Float fats_g) {
        this.fats_g = fats_g;
    }

    public Float getWeightLose() {
        return weightLose;
    }

    public void setWeightLose(Float weightLose) {
        this.weightLose = weightLose;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

