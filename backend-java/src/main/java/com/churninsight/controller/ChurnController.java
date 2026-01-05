package com.churninsight.controller;

import com.churninsight.dto.ChurnPredictionResponse;
import com.churninsight.model.ChurnInput;
import com.churninsight.model.ChurnPrediction;
import com.churninsight.model.ChurnPredictionRepository;
import com.churninsight.model.ChurnInputRepository;
import com.churninsight.service.ChurnPredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Controlador REST para predicción de churn.
 */

@RestController
@RequestMapping("/api/churn")
@Tag(name = "Churn Prediction API", description = "Endpoints para predicción de churn")
public class ChurnController {
    private final ChurnPredictionService churnPredictionService;
    private final ChurnInputRepository churnInputRepository;
    private final ChurnPredictionRepository churnPredictionRepository;

    public ChurnController(ChurnPredictionService churnPredictionService, 
                          ChurnInputRepository churnInputRepository,
                          ChurnPredictionRepository churnPredictionRepository) {
        this.churnPredictionService = churnPredictionService;
        this.churnInputRepository = churnInputRepository;
        this.churnPredictionRepository = churnPredictionRepository;
    }

    /**
     * Predicción individual vía POST - Guarda en BD y retorna resultado completo.
     */
    @Operation(summary = "Predicción individual de churn")
    @PostMapping("/predict")
    public ChurnPredictionResponse predictChurn(@RequestBody ChurnInput input) {
        // Realizar la predicción
        double probability = churnPredictionService.predict(input);
        
        // Generar un customer_id único
        String customerId = "CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Guardar la predicción en la base de datos
        ChurnPrediction prediction = new ChurnPrediction(
            (int) input.getAgeRisk(), // Convertir float a int
            input.getNumOfProducts(),
            input.getInactivo4070(),
            input.getProductsRiskFlag(),
            input.getCountryRiskFlag(),
            probability,
            customerId
        );
        churnPredictionRepository.save(prediction);
        
        // Retornar respuesta completa
        return new ChurnPredictionResponse(probability, customerId);
    }

    /**
     * Predicción en lote desde la base de datos.
     */
    @Operation(summary = "Predicción en lote de churn")
    @GetMapping("/predict-all")
    public List<Double> predictAllChurn() {
        return churnInputRepository.findAll().stream()
            .map(churnPredictionService::predict)
            .toList();
    }

    /**
     * Obtener historial de predicciones.
     */
    @Operation(summary = "Obtener historial de predicciones")
    @GetMapping("/history")
    public List<ChurnPrediction> getHistory() {
        return churnPredictionRepository.findTop50ByOrderByPredictionDateDesc();
    }
}
