package com.churninsight.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos del cliente para predicción de churn")
public class PredictionRequest {

    @NotBlank(message = "El ID del cliente es obligatorio")
    @JsonProperty("customer_id")
    @Schema(description = "Identificador único del cliente", example = "CUST-001")
    private String customerId;

    @NotNull(message = "Los cargos mensuales son obligatorios")
    @DecimalMin(value = "0.0", message = "Los cargos mensuales deben ser mayores o iguales a 0")
    @JsonProperty("monthly_charges")
    @Schema(description = "Cargos mensuales del cliente", example = "79.99")
    private Double monthlyCharges;

    @NotNull(message = "Los meses de permanencia son obligatorios")
    @Min(value = 0, message = "Los meses de permanencia deben ser mayores o iguales a 0")
    @JsonProperty("tenure_months")
    @Schema(description = "Meses de permanencia del cliente", example = "24")
    private Integer tenureMonths;

    @NotBlank(message = "El tipo de contrato es obligatorio")
    @JsonProperty("contract_type")
    @Schema(description = "Tipo de contrato del cliente", example = "Month-to-month")
    private String contractType;

    @NotBlank(message = "El servicio de internet es obligatorio")
    @JsonProperty("internet_service")
    @Schema(description = "Tipo de servicio de internet", example = "Fiber optic")
    private String internetService;

    @NotNull(message = "Los cargos totales son obligatorios")
    @DecimalMin(value = "0.0", message = "Los cargos totales deben ser mayores o iguales a 0")
    @JsonProperty("total_charges")
    @Schema(description = "Cargos totales acumulados", example = "1920.50")
    private Double totalCharges;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getMonthlyCharges() {
        return monthlyCharges;
    }

    public void setMonthlyCharges(Double monthlyCharges) {
        this.monthlyCharges = monthlyCharges;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getInternetService() {
        return internetService;
    }

    public void setInternetService(String internetService) {
        this.internetService = internetService;
    }

    public Double getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(Double totalCharges) {
        this.totalCharges = totalCharges;
    }
}
