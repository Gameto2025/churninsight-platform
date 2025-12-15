package com.churninsight.api;

import com.churninsight.api.dto.PredictionRequest;
import com.churninsight.api.dto.PredictionResponse;
import com.churninsight.domain.service.PredictionService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PredictController {

    private final PredictionService predictionService;

    public PredictController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping(value = "/predict", produces = MediaType.APPLICATION_JSON_VALUE)
    public PredictionResponse predict(@Valid @RequestBody PredictionRequest request) {
        return predictionService.predict(request);
    }
}
