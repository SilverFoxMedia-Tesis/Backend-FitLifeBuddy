package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Enum.ClassificationIMC;
import com.fitLifeBuddy.Entity.Enum.Gender;
import com.fitLifeBuddy.Entity.Enum.PhysicalActivity;
import com.fitLifeBuddy.Entity.Enum.TypeHealthCondition;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {
    public float calcularTMB(Float peso, Float altura, Long edad, Gender genero, PhysicalActivity actividadFisica) {
        String classificationIMC = calcularClasificacionIMC(peso, altura);
        if (classificationIMC.equals("Obesity_Type_II") || classificationIMC.equals("Obesity_Type_III")) {
            return calcularTMBMifflin(peso, altura, edad.intValue(), genero, actividadFisica);
        } else if (actividadFisica == PhysicalActivity.No) {
            return calcularTMBFAOONU(peso, edad.intValue(), genero);
        } else {
            return calcularTMBHarrisBenedict(peso, altura, edad.intValue(), genero, actividadFisica);
        }
    }

    private String calcularClasificacionIMC(Float peso, Float altura) {
        double imc = peso / Math.pow(altura / 100, 2);
        if (imc < 18.5) return "Insufficient_Weight";
        else if (imc <= 24.9) return "Normal_Weight";
        else if (imc <= 29.9) return "Overweight";
        else if (imc <= 34.9) return "Obesity_Type_I";
        else if (imc <= 39.9) return "Obesity_Type_II";
        else return "Obesity_Type_III";
    }

    public ClassificationIMC calcularClasificacionIMCEnum(Float peso, Float altura) {
        double imc = peso / Math.pow(altura / 100, 2);
        if (imc < 18.5) return ClassificationIMC.Insufficient_Weight;
        else if (imc <= 24.9) return ClassificationIMC.Normal_Weight;
        else if (imc <= 29.9) return ClassificationIMC.Overweight;
        else if (imc <= 34.9) return ClassificationIMC.Obesity_Type_I;
        else if (imc <= 39.9) return ClassificationIMC.Obesity_Type_II;
        else return ClassificationIMC.Obesity_Type_III;
    }

    private float calcularTMBMifflin(Float peso, Float altura, int edad, Gender genero, PhysicalActivity actividadFisica) {
        double baseTmb = genero == Gender.MALE ? 10 * peso + 6.25 * altura - 5 * edad + 5 : 10 * peso + 6.25 * altura - 5 * edad - 161;
        return (float) (baseTmb * obtenerMultiplicadorActividad(actividadFisica));
    }

    private float calcularTMBFAOONU(Float peso, int edad, Gender genero) {
        if (genero == Gender.MALE) {
            return edad <= 18 ? (float) (17.5 * peso + 651) : (float) (15.3 * peso + 679);
        } else {
            return edad <= 18 ? (float) (12.2 * peso + 746) : (float) (14.7 * peso + 496);
        }
    }

    private float calcularTMBHarrisBenedict(Float peso, Float altura, int edad, Gender genero, PhysicalActivity actividadFisica) {
        double baseTmb = genero == Gender.MALE ? 66.5 + (13.75 * peso) + (5.003 * altura) - (6.78 * edad) : 655 + (9.56 * peso) + (1.85 * altura) - (4.68 * edad);
        return (float) (baseTmb * obtenerMultiplicadorActividad(actividadFisica));
    }

    private double obtenerMultiplicadorActividad(PhysicalActivity actividad) {
        switch (actividad) {
            case Slight: return 1.375;
            case Moderate: return 1.55;
            case Strong: return 1.725;
            case Very_Strong: return 1.9;
            default: return 1.2; // Esto incluye "No"
        }
    }

    public float calcularDeficit(float tmb) {
        return tmb * 0.80f; // Calcula el 80% del TMB para obtener el déficit
    }

    public float calcularMetaMensual(float peso) {
        return peso * 0.02f; // Calcula el 2% del peso actual como meta mensual de pérdida
    }

    public int mapearActividadNumerica(PhysicalActivity actividad) {
        switch (actividad) {
            case No: return 1;
            case Slight: return 2;
            case Moderate: return 3;
            case Strong: return 4;
            case Very_Strong: return 5;
            default: return 1;
        }
    }

    public Macronutrientes calcularMacronutrientes(float deficit, TypeHealthCondition condicionSalud) {
        boolean tieneDiabetes = condicionSalud == TypeHealthCondition.DIABETES;
        float carbohidratos = (deficit * 0.50f) / 4;
        float grasas = (deficit * 0.30f) / 9;
        float proteinas = tieneDiabetes ? (deficit * 0.20f) / 4 / 0.8f : (deficit * 0.20f) / 4;

        // Calculando macronutrientes para comidas específicas
        float desayuno_carbs = carbohidratos * 0.25f;
        float desayuno_proten = proteinas * 0.25f;
        float desayuno_grasas = grasas * 0.25f;

        float almuerzo_carbs = carbohidratos * 0.35f;
        float almuerzo_proten = proteinas * 0.35f;
        float almuerzo_grasas = grasas * 0.35f;

        float cena_carbs = carbohidratos * 0.40f;
        float cena_proten = proteinas * 0.40f;
        float cena_grasas = grasas * 0.40f;

        return new Macronutrientes(carbohidratos,grasas,proteinas,desayuno_carbs, desayuno_proten, desayuno_grasas,
                almuerzo_carbs, almuerzo_proten, almuerzo_grasas,
                cena_carbs, cena_proten, cena_grasas);
    }

    public static class Macronutrientes {
        public float carbohidratos, proteinas, grasas;
        public float desayunoCarbs, desayunoProten, desayunoGrasas;
        public float almuerzoCarbs, almuerzoProten, almuerzoGrasas;
        public float cenaCarbs, cenaProten, cenaGrasas;

        public Macronutrientes(float carbohidratos_g,float proteinas_g,float grasas_g,
                               float desCarbs, float desProten, float desGrasas,
                               float almCarbs, float almProten, float almGrasas,
                               float cenCarbs, float cenProten, float cenGrasas) {
            this.carbohidratos = carbohidratos_g;
            this.proteinas = proteinas_g;
            this.grasas = grasas_g;
            this.desayunoCarbs = desCarbs;
            this.desayunoProten = desProten;
            this.desayunoGrasas = desGrasas;
            this.almuerzoCarbs = almCarbs;
            this.almuerzoProten = almProten;
            this.almuerzoGrasas = almGrasas;
            this.cenaCarbs = cenCarbs;
            this.cenaProten = cenProten;
            this.cenaGrasas = cenGrasas;
        }
    }
}
