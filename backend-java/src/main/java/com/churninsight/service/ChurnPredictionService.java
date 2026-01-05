
package com.churninsight.service;

import com.churninsight.model.ChurnInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Servicio para consumir el modelo de churn vía FastAPI.
 */
@Service
public class ChurnPredictionService {
    private static final String FASTAPI_URL = "http://localhost:8000/predict";
    private final ObjectMapper objectMapper;

    @Autowired
    public ChurnPredictionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Envía los datos al endpoint FastAPI y obtiene la probabilidad de churn.
     */
    public double predict(ChurnInput input) {
        String jsonInput = String.format("{\"Age_Risk\":%f,\"NumOfProducts\":%d,\"Inactivo_40_70\":%d,\"Products_Risk_Flag\":%d,\"Country_Risk_Flag\":%d}",
                input.getAgeRisk(),
                input.getNumOfProducts(),
                input.getInactivo4070(),
                input.getProductsRiskFlag(),
                input.getCountryRiskFlag()
        );
        try {
            URL url = new URL(FASTAPI_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] inputBytes = jsonInput.getBytes("utf-8");
                os.write(inputBytes, 0, inputBytes.length);
            }

            int code = conn.getResponseCode();
            if (code == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    // Espera respuesta tipo: {"churn_probability": valor}
                    String resp = response.toString();
                    return objectMapper.readTree(resp).get("churn_probability").asDouble();
                }
            } else {
                // Manejo profesional de errores
                throw new RuntimeException("Error en la respuesta del modelo: " + code);
            }
        } catch (Exception e) {
            // Manejo profesional de errores
            throw new RuntimeException("Error al consumir el modelo de churn", e);
        }
    }
}
