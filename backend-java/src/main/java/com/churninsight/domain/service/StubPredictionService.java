package com.churninsight.domain.service;

import com.churninsight.api.dto.PredictionRequest;
import com.churninsight.api.dto.PredictionResponse;
import org.springframework.stereotype.Service;

@Service
public class StubPredictionService implements PredictionService {

    @Override
    public PredictionResponse predict(PredictionRequest request) {
        // Stub inicial para que el equipo pueda integrar frontend/QA.
        // En el siguiente paso se reemplaza por integración con el microservicio Python o el modelo embebido.
        double probability = 0.50;
        String forecast = "medio_riesgo";
        return new PredictionResponse(forecast, probability);
    }
}
