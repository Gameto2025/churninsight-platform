package com.churninsight.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PredictionRequest {

    @NotBlank
    @JsonProperty("customer_id")
    private String customerId;

    @NotNull
    @DecimalMin("0.0")
    @JsonProperty("monthly_charges")
    private Double monthlyCharges;

    @NotNull
    @Min(0)
    @JsonProperty("tenure_months")
    private Integer tenureMonths;

    @NotBlank
    @JsonProperty("contract_type")
    private String contractType;

    @NotBlank
    @JsonProperty("internet_service")
    private String internetService;

    @NotNull
    @DecimalMin("0.0")
    @JsonProperty("total_charges")
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
