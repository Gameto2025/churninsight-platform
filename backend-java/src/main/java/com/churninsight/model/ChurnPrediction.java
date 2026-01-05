package com.churninsight.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que almacena tanto los datos de entrada como el resultado de la predicción.
 */
@Entity
@Table(name = "churn_predictions")
public class ChurnPrediction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos de entrada
    @Column(name = "age_risk", nullable = false)
    private int ageRisk;

    @Column(name = "num_of_products", nullable = false)
    private int numOfProducts;

    @Column(name = "inactivo_40_70", nullable = false)
    private int inactivo4070;

    @Column(name = "products_risk_flag", nullable = false)
    private int productsRiskFlag;

    @Column(name = "country_risk_flag", nullable = false)
    private int countryRiskFlag;

    // Resultado de la predicción
    @Column(name = "churn_probability", nullable = false)
    private double churnProbability;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "prediction_date", nullable = false)
    private LocalDateTime predictionDate;

    // Constructor por defecto
    protected ChurnPrediction() {
        this.predictionDate = LocalDateTime.now();
    }

    // Constructor completo
    public ChurnPrediction(int ageRisk, int numOfProducts, int inactivo4070, 
                          int productsRiskFlag, int countryRiskFlag, 
                          double churnProbability, String customerId) {
        this.ageRisk = ageRisk;
        this.numOfProducts = numOfProducts;
        this.inactivo4070 = inactivo4070;
        this.productsRiskFlag = productsRiskFlag;
        this.countryRiskFlag = countryRiskFlag;
        this.churnProbability = churnProbability;
        this.customerId = customerId;
        this.predictionDate = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAgeRisk() {
        return ageRisk;
    }

    public void setAgeRisk(int ageRisk) {
        this.ageRisk = ageRisk;
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public void setNumOfProducts(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    public int getInactivo4070() {
        return inactivo4070;
    }

    public void setInactivo4070(int inactivo4070) {
        this.inactivo4070 = inactivo4070;
    }

    public int getProductsRiskFlag() {
        return productsRiskFlag;
    }

    public void setProductsRiskFlag(int productsRiskFlag) {
        this.productsRiskFlag = productsRiskFlag;
    }

    public int getCountryRiskFlag() {
        return countryRiskFlag;
    }

    public void setCountryRiskFlag(int countryRiskFlag) {
        this.countryRiskFlag = countryRiskFlag;
    }

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

    public LocalDateTime getPredictionDate() {
        return predictionDate;
    }

    public void setPredictionDate(LocalDateTime predictionDate) {
        this.predictionDate = predictionDate;
    }
}
