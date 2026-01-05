from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

class ChurnInput(BaseModel):
    Age_Risk: float
    NumOfProducts: int
    Inactivo_40_70: int
    Products_Risk_Flag: int
    Country_Risk_Flag: int

@app.post("/predict")
def predict_churn(data: ChurnInput):
    # Aquí va la lógica de predicción usando modelo_churn_banco.pmml
    return {"churn_probability": 0.0}
