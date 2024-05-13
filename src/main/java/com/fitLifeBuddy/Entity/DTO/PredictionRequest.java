package com.fitLifeBuddy.Entity.DTO;

import com.fitLifeBuddy.Entity.Plan;

public class PredictionRequest {
    private float calorieDeficit;
    private float desayunoCarbs;
    private float desayunoProten;
    private float desayunoGrasas;
    private float almuerzoCarbs;
    private float almuerzoProten;
    private float almuerzoGrasas;
    private float cenaCarbs;
    private float cenaProten;
    private float cenaGrasas;
    private float abdominalCircumference;
    private int physicalActivity;

    // Constructor que recibe todos los parámetros
    public PredictionRequest(Plan plan) {
        this.calorieDeficit = plan.getCalorieDeficit();
        this.desayunoCarbs = plan.getDesayuno_carbs();
        this.desayunoProten = plan.getDesayuno_proten();
        this.desayunoGrasas = plan.getDesayuno_grasas();
        this.almuerzoCarbs = plan.getAlmuerzo_carbs();
        this.almuerzoProten = plan.getAlmuerzo_proten();
        this.almuerzoGrasas = plan.getAlmuerzo_grasas();
        this.cenaCarbs = plan.getCena_carbs();
        this.cenaProten = plan.getCena_proten();
        this.cenaGrasas = plan.getCena_grasas();
        this.abdominalCircumference = plan.getAbdominal_circumference();
        this.physicalActivity = plan.getPhysical_activity().intValue();
    }

    public float getDesayunoProten() {
        return desayunoProten;
    }

    public void setDesayunoProten(float desayunoProten) {
        this.desayunoProten = desayunoProten;
    }

    public float getAlmuerzoProten() {
        return almuerzoProten;
    }

    public void setAlmuerzoProten(float almuerzoProten) {
        this.almuerzoProten = almuerzoProten;
    }

    public int getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(int physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public float getAbdominalCircumference() {
        return abdominalCircumference;
    }

    public void setAbdominalCircumference(float abdominalCircumference) {
        this.abdominalCircumference = abdominalCircumference;
    }

    public float getCenaGrasas() {
        return cenaGrasas;
    }

    public void setCenaGrasas(float cenaGrasas) {
        this.cenaGrasas = cenaGrasas;
    }

    public float getCenaCarbs() {
        return cenaCarbs;
    }

    public void setCenaCarbs(float cenaCarbs) {
        this.cenaCarbs = cenaCarbs;
    }

    public float getCenaProten() {
        return cenaProten;
    }

    public void setCenaProten(float cenaProten) {
        this.cenaProten = cenaProten;
    }

    public float getAlmuerzoGrasas() {
        return almuerzoGrasas;
    }

    public void setAlmuerzoGrasas(float almuerzoGrasas) {
        this.almuerzoGrasas = almuerzoGrasas;
    }

    public float getAlmuerzoCarbs() {
        return almuerzoCarbs;
    }

    public void setAlmuerzoCarbs(float almuerzoCarbs) {
        this.almuerzoCarbs = almuerzoCarbs;
    }

    public float getDesayunoGrasas() {
        return desayunoGrasas;
    }

    public void setDesayunoGrasas(float desayunoGrasas) {
        this.desayunoGrasas = desayunoGrasas;
    }

    public float getDesayunoCarbs() {
        return desayunoCarbs;
    }

    public void setDesayunoCarbs(float desayunoCarbs) {
        this.desayunoCarbs = desayunoCarbs;
    }

    public float getCalorieDeficit() {
        return calorieDeficit;
    }

    public void setCalorieDeficit(float calorieDeficit) {
        this.calorieDeficit = calorieDeficit;
    }

    @Override
    public String toString() {
        return "PredictionRequest{" +
                "calorieDeficit=" + calorieDeficit +
                ", desayunoCarbs=" + desayunoCarbs +
                ", desayunoProten=" + desayunoProten +
                ", desayunoGrasas=" + desayunoGrasas +
                ", almuerzoCarbs=" + almuerzoCarbs +
                ", almuerzoProten=" + almuerzoProten +
                ", almuerzoGrasas=" + almuerzoGrasas +
                ", cenaCarbs=" + cenaCarbs +
                ", cenaProten=" + cenaProten +
                ", cenaGrasas=" + cenaGrasas +
                ", abdominalCircumference=" + abdominalCircumference +
                ", physicalActivity=" + physicalActivity +
                '}';
    }
}
