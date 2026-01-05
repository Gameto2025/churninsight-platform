package com.churninsight.model;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para acceder a los datos de entrada del modelo de churn.
 * Extiende JpaRepository para operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface ChurnInputRepository extends JpaRepository<ChurnInput, Long> {
    // Métodos personalizados si se requieren
}
