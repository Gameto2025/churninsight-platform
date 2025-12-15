# ChurnInsight

Plataforma para **predicción de churn** y **retención proactiva** (Estrategia 2025).
Convierte datos de clientes en **acción inmediata** mediante una API de predicción y un flujo de Data Science para entrenamiento y entrega del modelo.

## Objetivo (Estrategia de Retención Proactiva 2025)

- **Enfoque proactivo:** detectar riesgo temprano y habilitar intervención preventiva.
- **Integración ChurnInsight:** predicción en tiempo real, segmentación y priorización por riesgo.
- **Personalización inteligente:** decisiones/acciones basadas en riesgo y valor (LTV) evitando promociones genéricas.

## Arquitectura (visión de trabajo)

- **Backend (Spring Boot / Java):** “cerebro” del sistema: contrato REST, validación, orquestación y persistencia (cuando aplique).
- **Data Science (Python / ML):** notebooks + entrenamiento + métricas; entrega del artefacto del modelo.

> Nota sobre integración del modelo (según documentación):
>
> - **MVP:** Data Science entrega `modelo_churn.joblib` y el backend lo carga para predicciones.
> - **Evolución (target):** exponer predicción como microservicio Python (FastAPI) con caché (Redis) y datos históricos (PostgreSQL).
>   El **contrato JSON** se mantiene igual.

---

## Estructura del repositorio

```
ChurnInsight/
├── backend-java/          # Spring Boot (API REST)
├── data-science/          # Python / ML (notebooks + modelo)
│   ├── notebooks/         # EDA, feature engineering, entrenamiento, métricas
│   └── model/             # modelo_churn.joblib (salida de entrenamiento)
└── README.md              # Documentación principal
```

---

## Backend (Spring Boot / Java)

### Requisitos

- Java 17+
- Maven 3.9+

### Ejecutar en local (Windows)

```bash
mvn -f backend-java/pom.xml spring-boot:run
```

URLs útiles:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Health: `http://localhost:8080/actuator/health`

### Componentes esperados (para escalar sin perder mantenibilidad)

- **Controller:** recibe requests HTTP y valida.
- **Service:** lógica de negocio/orquestación (predicción, umbrales, etc.).
- **DTOs:** `PredictionRequest`, `PredictionResponse`.
- **Integración de modelo:** carga/uso del modelo o llamada a microservicio (según fase).

---

## API REST (Contrato de integración)

### Endpoint principal

`POST /api/v1/predict`

#### Request (JSON)

```json
{
  "customer_id": "12345",
  "monthly_charges": 65.5,
  "tenure_months": 24,
  "contract_type": "month-to-month",
  "internet_service": "fiber_optic",
  "total_charges": 1572.0
}
```

#### Response (JSON)

```json
{
  "prevision": "alto_riesgo",
  "probabilidad": 0.87
}
```

#### Reglas (obligatorias)

- `prevision`: `bajo_riesgo` | `medio_riesgo` | `alto_riesgo`
- `probabilidad`: número entre `0` y `1`
- El JSON usa **snake_case** (por integración con Data Science). En Java se mapea a camelCase con Jackson.

#### Umbrales de clasificación (MVP, ajustables)

- `probabilidad > 0.70` → `alto_riesgo`
- `0.40 <= probabilidad <= 0.70` → `medio_riesgo`
- `probabilidad < 0.40` → `bajo_riesgo`

#### Ejemplo de prueba (curl)

```bash
curl -X POST "http://localhost:8080/api/v1/predict" ^
  -H "Content-Type: application/json" ^
  -d "{\"customer_id\":\"12345\",\"monthly_charges\":65.5,\"tenure_months\":24,\"contract_type\":\"month-to-month\",\"internet_service\":\"fiber_optic\",\"total_charges\":1572.0}"
```

---

## Demostración funcional (API en acción)

Flujo esperado end-to-end:

1. **Petición HTTP:** se envían datos del cliente a `/api/v1/predict`.
2. **Procesamiento backend:** Spring Boot recibe y valida el payload.
3. **Carga del modelo:** se carga `modelo_churn.joblib` (modo MVP) o se invoca el microservicio Python (modo target).
4. **Predicción:** se calcula `probabilidad` de churn.
5. **Respuesta JSON:** se retorna `prevision` + `probabilidad`.

Métricas a mostrar en demo (según estrategia):

- **Latencia** (objetivo: predicción bajo demanda **< 200ms**)
- **Precisión del modelo:** accuracy / F1-score (reportado por Data Science)
- **Confianza:** valor `probabilidad`

---

## Data Science (Python / ML)

Objetivo: entrenar el modelo y producir el artefacto serializado para consumo por backend.

Entregables:

- Notebooks: `data-science/notebooks/`
- Modelo serializado: `data-science/model/modelo_churn.joblib`

Proceso (resumen):

1. EDA (patrones en churn histórico)
2. Feature engineering (150+ variables)
3. Entrenamiento (Random Forest / XGBoost / etc.)
4. Validación (AUC-ROC, precision, recall, F1)
5. Serialización (`modelo_churn.joblib`)

---

## Sincronización crítica: Data Science ↔ Backend

### Contrato de integración “inmutable” (durante MVP)

Cualquier cambio del JSON de entrada/salida requiere coordinación previa y aprobación conjunta.

Checklist mínimo:

- Tipos numéricos: float/double sin truncamientos
- Codificación: UTF‑8
- Booleanos: `true/false`
- Nullability: campos opcionales definidos
- Formato fechas/horas (si se incorporan): ISO 8601 UTC
- Nombres de campos: snake_case consistente (mappings documentados en backend)

### Gestión de errores (documentado para implementar)

Estrategia objetivo:

- Timeout máximo de dependencia de predicción: **500ms**
- Si la predicción no está disponible: responder **503** “Servicio de predicción temporalmente no disponible”
- (Target) fallback con caché de última predicción (máx. 24h) + logging estructurado

---

## Objetivos Q1 (KPIs de referencia)

- Churn neto: 4% → **3.5%**
- Éxito de retención (alto riesgo contactados): **40%**
- Coste de retención: **<= 15%** del ARPU mensual

---

## Trabajo en equipo (mínimo)

- Backend trabaja dentro de `backend-java/`
- Data Science trabaja dentro de `data-science/`
- Cambios al contrato `/api/v1/predict`: coordinar **antes** de merge
