import os
import joblib
import numpy as np
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware

# 1. Configuración de la ruta del modelo (Basado en tus fotos de GitHub)
# Como el .pkl está en la misma carpeta que este script, usamos la raíz.
BASE_DIR = os.path.dirname(__file__)
MODEL_PATH = os.path.join(BASE_DIR, "modelo_Banco_churn.pkl")

# 2. Definición de esquemas de datos
class PredictRequest(BaseModel):
    features: list[float]

class PredictResponse(BaseModel):
    prediction: int
    probability: float

# 3. Inicialización de FastAPI
app = FastAPI(title="Churn Predictor")

# Configuración de CORS
origins = ["*"] # Permitimos todos para facilitar la conexión con el frontend
app.add_middleware(
    CORSMiddleware, 
    allow_origins=origins, 
    allow_credentials=True, 
    allow_methods=["*"], 
    allow_headers=["*"]
)

# 4. Carga del modelo usando joblib (más seguro para Scikit-Learn)
model = None
try:
    if os.path.exists(MODEL_PATH):
        model = joblib.load(MODEL_PATH)
    else:
        # Esto ayuda a diagnosticar errores en los logs de Streamlit
        print(f"ERROR: Archivo no encontrado en {MODEL_PATH}")
        print(f"Archivos presentes en el directorio: {os.listdir(BASE_DIR)}")
except Exception as e:
    print(f"Error crítico al cargar el modelo: {e}")

# 5. Endpoints
@app.get("/health")
def health():
    return {
        "status": "ok", 
        "model_loaded": model is not None,
        "path_checked": MODEL_PATH
    }

@app.post("/predict", response_model=PredictResponse)
def predict(req: PredictRequest):
    if model is None:
        raise HTTPException(status_code=503, detail="Modelo no cargado en el servidor")
    
    try:
        # Convertir entrada a array de numpy (formato 2D esperado por sklearn)
        data = np.array(req.features).reshape(1, -1)
        
        # Realizar predicción
        pred = int(model.predict(data)[0])
        
        # Calcular probabilidad si el modelo lo permite
        if hasattr(model, "predict_proba"):
            pred_proba = float(model.predict_proba(data)[0, 1])
        else:
            pred_proba = 1.0 if pred == 1 else 0.0
            
        return PredictResponse(prediction=pred, probability=pred_proba)
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
