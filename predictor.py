import os
import joblib  # <--- IMPORTANTE: Faltaba esta línea
import numpy as np
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware

# 1. Configuración de la ruta del modelo
# Asegúrate de que el nombre del archivo coincida exactamente con el que subiste a GitHub
MODEL_FILENAME = "modelo_Banco_churn.pkl" 
MODEL_PATH = os.path.join(os.path.dirname(__file__), "models", MODEL_FILENAME)

# Si el archivo está en la raíz (fuera de la carpeta models), usa esta línea:
# MODEL_PATH = os.path.join(os.path.dirname(__file__), MODEL_FILENAME)

# 2. Definición de esquemas de datos
class PredictRequest(BaseModel):
    features: list[float]

class PredictResponse(BaseModel):
    prediction: int
    probability: float

# 3. Inicialización de FastAPI
app = FastAPI(title="Churn Predictor")

# Configuración de CORS
origins = [
    "http://localhost:3000",
    "http://localhost:8080",
    "http://127.0.0.1:3000",
    "https://platform-churninsight.streamlit.app", # Añadido para Streamlit
]
app.add_middleware(
    CORSMiddleware, 
    allow_origins=origins, 
    allow_credentials=True, 
    allow_methods=["*"], 
    allow_headers=["*"]
)

# 4. Carga robusta del modelo
if not os.path.exists(MODEL_PATH):
    # Esto te ayudará a ver en los logs qué archivos realmente ve Python
    available_files = os.listdir(os.path.dirname(MODEL_PATH) if os.path.dirname(MODEL_PATH) else ".")
    raise FileNotFoundError(f"No se encontró el modelo en {MODEL_PATH}. Archivos disponibles: {available_files}")

try:
    model = joblib.load(MODEL_PATH)
except Exception as e:
    # Si falla joblib, intentamos capturar el error específico
    raise RuntimeError(f"Error crítico al cargar el modelo: {str(e)}")

# 5. Endpoints
@app.get("/health")
def health():
    return {"status": "ok", "model_loaded": model is not None}

@app.post("/predict", response_model=PredictResponse)
def predict(req: PredictRequest):
    try:
        # Convertimos a formato que Scikit-Learn entiende (Matriz 2D)
        data = np.array(req.features).reshape(1, -1)
        
        # Predicción de clase
        pred = int(model.predict(data)[0])
        
        # Predicción de probabilidad (si el modelo lo permite)
        if hasattr(model, "predict_proba"):
            pred_proba = float(model.predict_proba(data)[0, 1])
        else:
            pred_proba = 1.0 if pred == 1 else 0.0
            
        return PredictResponse(prediction=pred, probability=pred_proba)
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error en la predicción: {str(e)}")
