# 🎯 ChurnInsight Platform

**Sistema Full-Stack profesional de predicción de churn con ML - Production Ready**

Plataforma completa para detectar y visualizar qué clientes tienen riesgo de abandonar tu servicio. Incluye modelo ML (100% accuracy), API REST Java, frontend React interactivo, y persistencia en MySQL.

---

## ⚡ Inicio Rápido (3-5 minutos)

```bash
# 1. Entrenar modelo y desplegar (automático)
cd data-science
python scripts/quick_start.py

# 2. Iniciar API Java (en otra terminal)
cd backend-java
mvn spring-boot:run

# 3. Iniciar Frontend React (en otra terminal)
cd frontend
npm install
npm start

# 4. Abrir navegador
# http://localhost:3000 (Frontend)
# http://localhost:8080 (API)

# 5. Probar predicción vía API
curl -X POST http://localhost:8080/api/predict \
  -H "Content-Type: application/json" \
  -d '{
    "Age": 35,
    "Income_Level": "High",
    "Total_Transactions": 150,
    "Avg_Transaction_Value": 1000,
    "Active_Days": 350,
    "App_Usage_Frequency": "Daily",
    "Customer_Satisfaction_Score": 9,
    "Last_Transaction_Days_Ago": 5
  }'

# Response: {"prediction": 0, "probability": 0.0012}
```

---

## 📊 Estado Actual

| Componente        | Estado        | Detalles                               |
| ----------------- | ------------- | -------------------------------------- |
| **Modelo ML**     | ✅ Entrenado  | 100% accuracy, AUC-ROC 1.000           |
| **API REST**      | ✅ Funcional  | Spring Boot en puerto 8080             |
| **Frontend**      | ✅ Funcional  | React + TypeScript en puerto 3000      |
| **Base de Datos** | ✅ MySQL      | Historial de predicciones              |
| **Despliegue**    | ✅ Automático | Scripts validados y funcionando        |
| **Documentación** | ✅ Completa   | README + QUICKSTART + PRODUCTION_SETUP |

---

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────────────┐
│  Frontend React (localhost:3000)                    │
│  ├─ Dashboard Panel                                 │
│  ├─ Prediction Form                                 │
│  ├─ Results Visualization                           │
│  └─ History Panel                                   │
└────────────────┬────────────────────────────────────┘
                 │ HTTP/REST
                 ↓
┌─────────────────────────────────────────────────────┐
│  Backend Spring Boot (localhost:8080)               │
│  ├─ /api/predict   → Predicciones                   │
│  ├─ /api/history   → Historial                      │
│  ├─ /api/stats     → Estadísticas                   │
│  └─ /api/health    → Health Check                   │
└────────────────┬────────────────────────────────────┘
                 │
        ┌────────┴────────┐
        │                 │
        ↓                 ↓
┌──────────────┐  ┌──────────────┐
│  ML Service  │  │    MySQL     │
│ churn_model  │  │ predictions  │
│   .pkl       │  │    table     │
└──────────────┘  └──────────────┘
```

---

## 📁 Estructura del Proyecto

```
churninsight-platform/
│
├── backend-java/
│   ├── src/main/java/com/churninsight/
│   │   ├── api/           # Controllers (Predict, History, Stats)
│   │   │   ├── PredictController.java
│   │   │   ├── HistoryController.java
│   │   │   ├── StatsController.java
│   │   │   └── dto/       # Request/Response DTOs
│   │   ├── config/        # WebConfig (CORS)
│   │   ├── domain/
│   │   │   ├── entity/    # Prediction JPA entity
│   │   │   ├── repository/ # Spring Data repositories
│   │   │   └── service/   # FastApiPredictionService
│   ├── src/resources/
│   │   ├── application.yml
│   │   └── db/mysql-setup.sql
│   └── pom.xml            # Dependencias Maven
│
├── frontend/
│   ├── src/
│   │   ├── components/    # Header, Dashboard, History
│   │   │   ├── Header.tsx
│   │   │   ├── DashboardPanel.tsx
│   │   │   └── HistoryPanel.tsx
│   │   ├── services/      # API client
│   │   │   └── api.ts
│   │   ├── App.tsx        # Main component
│   │   ├── PredictionForm.tsx
│   │   ├── PredictionResults.tsx
│   │   ├── theme.ts       # Material-UI theme
│   │   └── types.ts       # TypeScript types
│   ├── public/            # Static assets
│   ├── package.json
│   └── tsconfig.json
│
├── data-science/
│   ├── scripts/
│   │   ├── quick_start.py             # Inicio automático
│   │   ├── train_model_final.py       # Entrenamiento
│   │   ├── deploy_model.py            # Despliegue
│   │   ├── generate_synthetic_data.py # Generador de datos
│   │   └── README.md                  # Documentación scripts
│   │
│   ├── models/
│   │   ├── churn_model.pkl            # Modelo en producción ✅
│   │   └── churn_model_backup_*.pkl   # Backups automáticos
│   │
│   ├── data/
│   │   ├── dataset.csv                # 7,000 registros
│   │   ├── dataset_train.csv          # 4,900 (entrenamiento)
│   │   └── dataset_test.csv           # 2,100 (validación)
│   │
│   ├── logs/
│   │   ├── training_metrics.json      # Métricas de test
│   │   ├── deployment_checklist.json  # Validaciones
│   │   └── deployment_log.json        # Historial despliegues
│   │
│   ├── src/                           # Código modular reutilizable
│   ├── tests/                         # Tests unitarios (passing ✅)
│   └── requirements.txt               # Dependencias Python
│
├── README.md                          # Este archivo
├── QUICKSTART.md                      # Guía rápida
├── PRODUCTION_SETUP.md                # Guía completa de despliegue
└── EXECUTIVE_SUMMARY.md               # Resumen ejecutivo
```

---

## 🔧 Requisitos

### Backend Java

```
✅ Java 17 or higher
✅ Maven 3.9+
✅ MySQL 8.0+
```

### Frontend React

```
✅ Node.js 16+
✅ npm 8+ or yarn
```

Instalar dependencias:

```bash
cd frontend
npm install
```

### Data Science Python

```
✅ Python 3.8+
✅ pip/conda
```

Instalar dependencias:

```bash
cd data-science
pip install -r requirements.txt
```

---

## 📊 Métricas del Modelo

**Entrenamiento:** 4,900 registros  
**Validación:** 2,100 registros

| Métrica   | Valor | Threshold |
| --------- | ----- | --------- |
| Accuracy  | 100%  | ≥ 80% ✅  |
| Precision | 100%  | ≥ 75% ✅  |
| Recall    | 100%  | ≥ 70% ✅  |
| AUC-ROC   | 1.000 | ≥ 0.85 ✅ |
| F1-Score  | 100%  | - ✅      |

**Test de Predicciones:**

- Cliente activo reciente: 0.56% riesgo ✅
- Cliente medio: 3.47% riesgo ✅
- Cliente inactivo 200 días: 99.42% riesgo ✅

---

## 🚀 API Endpoints

### Health Check

```bash
GET /api/health
```

Response: `{"status":"UP"}`

### Realizar Predicción (Principal)

```bash
POST /api/predict
Content-Type: application/json

{
  "Age": 35,
  "Income_Level": "High",
  "Total_Transactions": 150,
  "Avg_Transaction_Value": 1000,
  "Active_Days": 350,
  "App_Usage_Frequency": "Daily",
  "Customer_Satisfaction_Score": 9,
  "Last_Transaction_Days_Ago": 5
}
```

Response:

```json
{
  "prediction": 0,
  "probability": 0.0012,
  "churnRisk": "LOW"
}
```

### Ver Historial

```bash
GET /api/history
```

Response:

```json
[
  {
    "id": 1,
    "prediction": 0,
    "probability": 0.0012,
    "churnRisk": "LOW",
    "timestamp": "2025-12-27T23:20:00"
  },
  {
    "id": 2,
    "prediction": 1,
    "probability": 0.9956,
    "churnRisk": "HIGH",
    "timestamp": "2025-12-27T23:21:00"
  }
]
```

### Estadísticas

```bash
GET /api/stats
```

Response:

```json
{
  "totalPredictions": 156,
  "churnRate": 0.23,
  "avgChurnProbability": 0.34,
  "highRiskCount": 36,
  "mediumRiskCount": 45,
  "lowRiskCount": 75
}
```

---

## 🎯 Ejecutar Todo

### Opción 1: Automático (Recomendado)

```bash
# Terminal 1: Preparar modelo ML
cd data-science
python scripts/quick_start.py

# Terminal 2: Backend API
cd backend-java
mvn spring-boot:run

# Terminal 3: Frontend React
cd frontend
npm install
npm start
```

### Opción 2: Paso a Paso

```bash
# 1. Generar datos
cd data-science
python scripts/generate_synthetic_data.py

# 2. Entrenar modelo
python scripts/train_model_final.py

# 3. Validar y desplegar
python scripts/deploy_model.py

# 4. Iniciar backend (Terminal 2)
cd ../backend-java
mvn spring-boot:run

# 5. Iniciar frontend (Terminal 3)
cd ../frontend
npm install
npm start
```

### Acceder a la Plataforma

- **Frontend UI:** http://localhost:3000
- **API Backend:** http://localhost:8080
- **API Docs:** http://localhost:8080/swagger-ui.html (próximamente)

---

## ✅ Validaciones

- ✅ Modelo cargable desde pkl
- ✅ Métricas disponibles y válidas
- ✅ Performance en thresholds mínimos
- ✅ Predicciones funcionando correctamente
- ✅ Backups automáticos antes de despliegue
- ✅ Historial de despliegues loguado
- ✅ Todos los tests pasando

---

## 📚 Documentación Completa

| Documento                          | Para Qué                         |
| ---------------------------------- | -------------------------------- |
| **QUICKSTART.md**                  | Empezar rápido (5 min)           |
| **PRODUCTION_SETUP.md**            | Setup completo y troubleshooting |
| **EXECUTIVE_SUMMARY.md**           | Resumen para stakeholders        |
| **data-science/scripts/README.md** | Documentación de scripts Python  |

---

## 🐛 Troubleshooting

### Error: "Dataset not found"

```bash
cd data-science && python scripts/generate_synthetic_data.py
```

### Error: "Model file not found"

```bash
cd data-science && python scripts/train_model_final.py
```

### Error: "Connection refused" (puerto 8080)

```bash
cd backend-java && mvn spring-boot:run
```

### Error: "MySQL connection error"

```bash
mysql -u root -p -e "SELECT 1"
```

### Error: "CORS blocked" en frontend

Verificar que WebConfig.java tenga configurado CORS para http://localhost:3000

### Error: "npm install failed"

```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

Ver PRODUCTION_SETUP.md para más detalles.

---

## 📞 Información de Contacto

- **Repositorio:** https://github.com/[usuario]/churninsight-platform
- **Issues:** GitHub Issues
- **Documentación:** Ver archivos .md en la raíz

---

## 🎓 Tecnologías

- **Frontend:** React 18, TypeScript, Material-UI (MUI)
- **Backend:** Java 17, Spring Boot 3.4.0, Spring Data JPA
- **ML:** Python 3.8+, scikit-learn, RandomForest
- **DB:** MySQL 8.0
- **Serialización:** joblib
- **Build:** Maven (backend), npm (frontend)
- **Testing:** JUnit (backend), Jest (frontend)
- **Versionado:** Git

---

## ✨ Características del Frontend

- ✅ **Dashboard Interactivo:** Visualización en tiempo real de predicciones
- ✅ **Formulario Dinámico:** Entrada de datos del cliente con validación
- ✅ **Resultados Visuales:** Gráficos de probabilidad y nivel de riesgo
- ✅ **Panel de Historial:** Tabla con todas las predicciones pasadas
- ✅ **Estadísticas:** Métricas agregadas (tasa de churn, distribución de riesgo)
- ✅ **Diseño Responsivo:** Funciona en desktop, tablet y móvil
- ✅ **Tema Profesional:** UI moderna con Material-UI

## ✨ Próximas Mejoras

- [ ] Autenticación y autorización (JWT)
- [ ] Reentrenamiento automático (cron job)
- [ ] Docker containerization (Docker Compose)
- [ ] Cloud deployment (AWS/Azure/GCP)
- [ ] MLOps pipeline (MLflow)
- [ ] Monitoreo de data drift
- [ ] Feature store centralizado
- [ ] Tests E2E con Cypress
- [ ] CI/CD pipeline (GitHub Actions)

---

**Status:** ✅ Production Ready | **Última actualización:** 28 Dic 2025 | **Versión:** 2.0
