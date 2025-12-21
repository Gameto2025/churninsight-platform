package com.churninsight.api;

import com.churninsight.api.dto.PredictionRequest;
import com.churninsight.api.dto.PredictionResponse;
import com.churninsight.domain.service.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Prediction API", description = "API para predicción de churn de clientes")
public class PredictController {

    private static final Logger logger = LoggerFactory.getLogger(PredictController.class);
    private final PredictionService predictionService;

    public PredictController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    @Operation(
        summary = "Predecir churn de cliente", 
        description = "Realiza una predicción de churn basada en los datos del cliente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Predicción exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PredictionResponse> predict(@Valid @RequestBody PredictionRequest request) {
        long startTime = System.currentTimeMillis();
        
        PredictionResponse response = predictionService.predict(request);
        
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Predicción completada en {}ms para customer_id: {}", duration, request.getCustomerId());
        
        return ResponseEntity.ok(response);
    }
}
