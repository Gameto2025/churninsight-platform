package com.churninsight.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado de la predicción de churn")
public class PredictionResponse {

    @JsonProperty("customer_id")
    @Schema(description = "Identificador del cliente", example = "CUST-001")
    private String customerId;

    @JsonProperty("prevision")
    @Schema(description = "Previsión de churn (SI/NO)", example = "SI")
    private String prevision;

    @JsonProperty("probabilidad")
    @Schema(description = "Probabilidad de churn (0.0 a 1.0)", example = "0.75")
    private Double probabilidad;

    public PredictionResponse() {
    }

    public PredictionResponse(String customerId, String prevision, Double probabilidad) {
        this.customerId = customerId;
        this.prevision = prevision;
        this.probabilidad = probabilidad;
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

    public Double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Double probabilidad) {
        this.probabilidad = probabilidad;
    }
}
