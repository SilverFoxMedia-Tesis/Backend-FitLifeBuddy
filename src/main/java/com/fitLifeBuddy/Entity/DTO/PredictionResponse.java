package com.fitLifeBuddy.Entity.DTO;

import java.util.Map;

public class PredictionResponse {
    private Map<String, String> predictions;

    public Map<String, String> getPredictions() {
        return predictions;
    }

    public void setPredictions(Map<String, String> predictions) {
        this.predictions = predictions;
    }

    @Override
    public String toString() {
        return "PredictionResponse{" + "predictions=" + predictions + '}';
    }
}
