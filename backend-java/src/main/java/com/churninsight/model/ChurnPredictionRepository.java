package com.churninsight.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para acceder a las predicciones de churn almacenadas.
 */
@Repository
public interface ChurnPredictionRepository extends JpaRepository<ChurnPrediction, Long> {
    
    // Buscar predicciones por customer_id
    List<ChurnPrediction> findByCustomerId(String customerId);
    
    // Buscar predicciones en un rango de fechas
    List<ChurnPrediction> findByPredictionDateBetween(LocalDateTime start, LocalDateTime end);
    
    // Contar predicciones del día actual
    @Query("SELECT COUNT(p) FROM ChurnPrediction p WHERE DATE(p.predictionDate) = CURRENT_DATE")
    long countTodayPredictions();
    
    // Contar predicciones con alta probabilidad de churn
    long countByChurnProbabilityGreaterThan(double probability);
    
    // Obtener las últimas N predicciones
    List<ChurnPrediction> findTop50ByOrderByPredictionDateDesc();
}
