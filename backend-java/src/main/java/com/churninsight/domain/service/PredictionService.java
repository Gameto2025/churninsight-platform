package com.churninsight.domain.service;

import com.churninsight.api.dto.PredictionRequest;
import com.churninsight.api.dto.PredictionResponse;

public interface PredictionService {
    PredictionResponse predict(PredictionRequest request);
}
