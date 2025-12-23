package com.churninsight.domain.service;

import com.churninsight.api.dto.PredictionRequest;
import com.churninsight.api.dto.PredictionResponse;
import org.springframework.stereotype.Service;

@Service
public class StubPredictionService implements PredictionService {

    @Override
    public PredictionResponse predict(PredictionRequest request) {
        double probability = 0.76; 
        
        String prevision;
        if (probability > 0.70) {
            prevision = "Va a cancelar";
        } else if (probability >= 0.40) {
            prevision = "En observación";
        } else {
            prevision = "Va a continuar";
        }
        return new PredictionResponse(prevision, probability);
    }
}