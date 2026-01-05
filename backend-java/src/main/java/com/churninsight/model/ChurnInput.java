package com.churninsight.model;

import jakarta.persistence.*;


/**
 * Entidad JPA que representa los datos de entrada para el modelo de churn.
    * Cada instancia de esta clase corresponde a una fila en la tabla "churn_input".
 */
@Entity
@Table(name = "churn_input")
public class ChurnInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Age_Risk", nullable = false)
    private float ageRisk;

    @Column(name = "NumOfProducts", nullable = false)
    private int numOfProducts;

    @Column(name = "Inactivo_40_70", nullable = false)
    private int inactivo4070;

    @Column(name = "Products_Risk_Flag", nullable = false)
    private int productsRiskFlag;

    @Column(name = "Country_Risk_Flag", nullable = false)
    private int countryRiskFlag;

    /**
     * Constructor por defecto requerido por JPA.
     */
    protected ChurnInput() {}

    /**
     * Constructor completo para crear instancias de ChurnInput.
     */
    public ChurnInput(float ageRisk, int numOfProducts, int inactivo4070, int productsRiskFlag, int countryRiskFlag) {
        this.ageRisk = ageRisk;
        this.numOfProducts = numOfProducts;
        this.inactivo4070 = inactivo4070;
        this.productsRiskFlag = productsRiskFlag;
        this.countryRiskFlag = countryRiskFlag;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public float getAgeRisk() { return ageRisk; }
    public void setAgeRisk(float ageRisk) { this.ageRisk = ageRisk; }

    public int getNumOfProducts() { return numOfProducts; }
    public void setNumOfProducts(int numOfProducts) { this.numOfProducts = numOfProducts; }

    public int getInactivo4070() { return inactivo4070; }
    public void setInactivo4070(int inactivo4070) { this.inactivo4070 = inactivo4070; }

    public int getProductsRiskFlag() { return productsRiskFlag; }
    public void setProductsRiskFlag(int productsRiskFlag) { this.productsRiskFlag = productsRiskFlag; }

    public int getCountryRiskFlag() { return countryRiskFlag; }
    public void setCountryRiskFlag(int countryRiskFlag) { this.countryRiskFlag = countryRiskFlag; }
}
