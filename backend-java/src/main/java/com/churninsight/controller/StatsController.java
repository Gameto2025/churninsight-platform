package com.churninsight.controller;

import com.churninsight.model.ChurnPredictionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para estadísticas del sistema.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Stats API", description = "Endpoints para estadísticas del sistema")
public class StatsController {
    
    private final ChurnPredictionRepository churnPredictionRepository;

    public StatsController(ChurnPredictionRepository churnPredictionRepository) {
        this.churnPredictionRepository = churnPredictionRepository;
    }

    /**
     * Obtener estadísticas del sistema.
     */
    @Operation(summary = "Obtener estadísticas del sistema")
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        long totalPredictions = churnPredictionRepository.count();
        long todayPredictions = churnPredictionRepository.countTodayPredictions();
        
        // Calcular tasa de retención basada en las predicciones reales
        double retentionRate = 0.0;
        if (totalPredictions > 0) {
            long highRiskCount = churnPredictionRepository.countByChurnProbabilityGreaterThan(0.5);
            retentionRate = ((double)(totalPredictions - highRiskCount) / totalPredictions) * 100;
        }
        
        stats.put("totalPredictions", totalPredictions);
        stats.put("todayPredictions", todayPredictions);
        stats.put("activeUsers", totalPredictions); // Total de clientes analizados
        stats.put("retentionRate", Math.round(retentionRate * 10.0) / 10.0); // Redondear a 1 decimal
        
        return stats;
    }
}
