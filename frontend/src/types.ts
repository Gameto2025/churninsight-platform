// Types for API requests and responses

export interface ChurnPredictionRequest {
  ageRisk: number;
  numOfProducts: number;
  inactivo4070: number;
  productsRiskFlag: number;
  countryRiskFlag: number;
}

export interface ChurnPredictionResponse {
  churn_probability: number;
  prevision?: string;
  customer_id: string;
}

export interface ChurnFormData {
  ageRisk: number;
  numOfProducts: number;
  inactivo4070: number;
  productsRiskFlag: number;
  countryRiskFlag: number;
}

export type IncomeLevel = "Bajo" | "Medio" | "Alto";
export type AppUsageFrequency = "Diaria" | "Semanal" | "Mensual";
