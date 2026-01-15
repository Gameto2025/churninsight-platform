import streamlit as st
import joblib
import pandas as pd
import numpy as np
import os

# Configuración visual
st.set_page_config(page_title="Churn Insight Platform", page_icon="🏦")

st.title("🏦 Churn Insight: Predicción de Abandono")
st.markdown("Herramienta de análisis de riesgo para clientes bancarios.")

# 1. Cargar el modelo
MODEL_PATH = "modelo_Banco_churn.pkl"

@st.cache_resource
def load_model():
    if os.path.exists(MODEL_PATH):
        try:
            return joblib.load(MODEL_PATH)
        except Exception as e:
            st.error(f"Error técnico al cargar el modelo: {e}")
    return None

pipe_xgb = load_model()

if pipe_xgb is None:
    st.error(f"❌ No se encontró el archivo '{MODEL_PATH}' en la raíz del repositorio.")
else:
    # 2. Formulario de entrada de datos (Inputs simples para el usuario)
    with st.sidebar:
        st.header("Datos del Cliente")
        age = st.number_input("Edad del cliente", min_value=18, max_value=100, value=45)
        num_products = st.slider("Número de productos contratados", 1, 4, 1)
        cuenta_activa = st.selectbox("¿La cuenta está activa?", options=[1, 0], format_func=lambda x: "Sí" if x == 1 else "No")
        pais_nombre = st.selectbox("País de residencia", options=[0, 1, 2], format_func=lambda x: ["France", "Germany", "Spain"][x])

    # 3. Lógica de Negocio (Transformación de datos según tu código)
    # Estas son las variables que tu modelo XGBoost espera
    age_risk = int((age >= 40) and (age <= 70))
    inactivo_40_70 = int((age >= 40) and (age <= 70) and (cuenta_activa == 0))
    products_risk = int(num_products >= 3)
    
    # Mapeo de riesgo por país (de tu código)
    paises_riesgo = {0: 0, 1: 1, 2: 0}
    country_risk = paises_riesgo.get(pais_nombre, 0)

    # Crear el DataFrame con el orden de columnas exacto
    columnas_modelo = ['Age_Risk', 'NumOfProducts', 'Inactivo_40_70', 'Products_Risk_Flag', 'Country_Risk_Flag']
    datos_entrada = pd.DataFrame([{
        'Age_Risk': age_risk,
        'NumOfProducts': num_products,
        'Inactivo_40_70': inactivo_40_70,
        'Products_Risk_Flag': products_risk,
        'Country_Risk_Flag': country_risk
    }])[columnas_modelo]

    # 4. Botón de Predicción y Resultados
    if st.button("Analizar Riesgo de Abandono"):
        probabilidad = pipe_xgb.predict_proba(datos_entrada)[0, 1]
        umbral_optimo = 0.58
        
        # Clasificación de riesgo (de tu código)
        def nivel_riesgo(p):
            if p >= 0.75: return 'ALTO'
            elif p >= 0.58: return 'MEDIO'
            else: return 'BAJO'

        riesgo = nivel_riesgo(probabilidad)
        
        # Mostrar resultado con métricas
        st.divider()
        col1, col2 = st.columns(2)
        
        with col1:
            st.metric(label="Probabilidad de Abandono", value=f"{probabilidad:.2%}")
        
        with col2:
            color = "red" if riesgo == "ALTO" else "orange" if riesgo == "MEDIO" else "green"
            st.markdown(f"### Nivel de Riesgo: :{color}[{riesgo}]")

        if probabilidad >= umbral_optimo:
            st.error("⚠️ El modelo predice que el cliente **ABANDONARÁ** el banco.")
        else:
            st.success("✅ El modelo predice que el cliente **PERMANECERÁ** en el banco.")

        # Información técnica adicional (opcional)
        with st.expander("Ver datos procesados"):
            st.write(datos_entrada)
