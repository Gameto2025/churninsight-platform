package com.churninsight.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para la respuesta de predicción de churn.
 */
public class ChurnPredictionResponse {
    @JsonProperty("churn_probability")
    private double churnProbability;
    
    @JsonProperty("customer_id")
    private String customerId;
    
    private String prevision;

    public ChurnPredictionResponse(double churnProbability, String customerId) {
        this.churnProbability = churnProbability;
        this.customerId = customerId;
        this.prevision = calculatePrevision(churnProbability);
    }

    private String calculatePrevision(double probability) {
        if (probability >= 0.75) {
            return "RIESGO ALTO - Acción inmediata requerida";
        } else if (probability >= 0.58) {
            return "RIESGO MEDIO - Monitoreo cercano recomendado";
        } else {
            return "RIESGO BAJO - Cliente estable";
        }
    }

    // Getters y Setters
    public double getChurnProbability() {
        return churnProbability;
    }

    public void setChurnProbability(double churnProbability) {
        this.churnProbability = churnProbability;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPrevision() {
        return prevision;
    }

    public void setPrevision(String prevision) {
        this.prevision = prevision;
    }
}
