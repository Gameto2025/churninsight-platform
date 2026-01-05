"""
Script FastAPI para servir el modelo de Churn Banco
Compatible con frontend (camelCase) y backend Java (PascalCase)
"""
from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
import joblib
import os
import pandas as pd
import logging

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Cargar modelo PKL
MODEL_PATH = os.path.join(os.path.dirname(__file__), '..', 'model', 'modelo_Banco_churn.pkl')
model = joblib.load(MODEL_PATH)

app = FastAPI(title="Churn Prediction API")

# CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
def root():
    return {"status": "online", "model": "Churn Banco", "endpoint": "/predict"}

@app.post("/predict")
async def predict(request: Request):
    data = await request.json()
    logger.info(f"Received data: {data}")
    logger.info(f"Data type: {type(data)}")
    logger.info(f"Data keys: {list(data.keys()) if isinstance(data, dict) else 'Not a dict'}")
    
    # Intentar extraer los datos en diferentes formatos
    try:
        # Intentar formato camelCase (frontend)
        if 'ageRisk' in data:
            age_risk = float(data['ageRisk'])
            num_products = int(data['numOfProducts'])
            inactivo = int(data['inactivo4070'])
            products_flag = int(data['productsRiskFlag'])
            country_flag = int(data['countryRiskFlag'])
        # Intentar formato PascalCase (Java)
        elif 'Age_Risk' in data:
            age_risk = float(data['Age_Risk'])
            num_products = int(data['NumOfProducts'])
            inactivo = int(data['Inactivo_40_70'])
            products_flag = int(data['Products_Risk_Flag'])
            country_flag = int(data['Country_Risk_Flag'])
        else:
            logger.error(f"Unknown data format: {data.keys()}")
            return {"error": "Invalid data format", "received_keys": list(data.keys())}
        
        # Preparar datos para el modelo
        input_df = pd.DataFrame([{
            'Age_Risk': age_risk,
            'NumOfProducts': num_products,
            'Inactivo_40_70': inactivo,
            'Products_Risk_Flag': products_flag,
            'Country_Risk_Flag': country_flag
        }])
        
        logger.info(f"Input DataFrame: {input_df.to_dict()}")
        
        # Predicción
        prob = float(model.predict_proba(input_df)[0][1])
        
        logger.info(f"Prediction: {prob}")
        return {"churn_probability": prob}
        
    except Exception as e:
        logger.error(f"Error: {str(e)}", exc_info=True)
        return {"error": str(e), "received_data": data}
