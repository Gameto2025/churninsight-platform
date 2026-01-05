-- ChurnInsight Database Setup Script

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ANALISTA',
    reset_token VARCHAR(255),
    reset_token_expiry DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_reset_token (reset_token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla alineada al contrato del modelo de churn
CREATE TABLE IF NOT EXISTS churn_input (
	id INT AUTO_INCREMENT PRIMARY KEY,
	Age_Risk FLOAT NOT NULL,
	NumOfProducts INT NOT NULL,
	Inactivo_40_70 INT NOT NULL,
	Products_Risk_Flag INT NOT NULL,
	Country_Risk_Flag INT NOT NULL,
	user_id BIGINT,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Tabla de predicciones de churn
CREATE TABLE IF NOT EXISTS churn_prediction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(50) NOT NULL,
    churn_probability DOUBLE NOT NULL,
    prediction VARCHAR(20) NOT NULL,
    age INT,
    income_level VARCHAR(50),
    user_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_customer_id (customer_id),
    INDEX idx_created_at (created_at),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
