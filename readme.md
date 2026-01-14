# 🎵 API de Canciones - Spring Boot

API REST para gestionar canciones desarrollada con Spring Boot 4.0.1 y Java 17.

## 📋 Requisitos Previos

- Java 17
- Maven 3.6+
- MariaDB/MySQL
- Nginx (para producción)

---

## 🚀 Guía de Despliegue en Ubuntu/GCP

### **1. Preparar el Servidor**

```bash
# Actualizar paquetes
sudo apt update && sudo apt upgrade -y

# Instalar Java 17
sudo apt install openjdk-17-jdk -y

# Verificar instalación
java -version
```

### **2. Instalar y Configurar MariaDB**

```bash
# Instalar MariaDB
sudo apt install mariadb-server mariadb-client -y

# Iniciar servicio
sudo systemctl start mariadb
sudo systemctl enable mariadb

# Configurar seguridad (opcional)
sudo mysql_secure_installation
```

### **3. Crear Base de Datos y Usuario**

```bash
# Acceder a MariaDB
sudo mysql
```

Ejecutar dentro de MariaDB:

```sql
CREATE DATABASE cancionesdb;

DROP USER IF EXISTS 'root'@'localhost';
CREATE USER 'root'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
EXIT;
```

Verificar conexión:

```bash
mysql -u root -p
# Contraseña: password
SHOW DATABASES;
EXIT;
```

### **4. Instalar Maven y Compilar el Proyecto**

```bash
# Instalar Maven
sudo apt install maven -y

# Verificar instalación
mvn -version

# Copiar el código al servidor (desde tu máquina local)
# scp -r canciones/ usuario@servidor:~/

# Compilar el proyecto
cd ~/canciones
mvn clean package -DskipTests
```

El JAR se generará en: `target/canciones-0.0.1-SNAPSHOT.jar`

### **5. Instalar y Configurar Nginx**

```bash
# Instalar Nginx
sudo apt install nginx -y

# Iniciar y habilitar Nginx
sudo systemctl start nginx
sudo systemctl enable nginx

# Verificar estado
sudo systemctl status nginx
```

### **6. Configurar Nginx como Reverse Proxy**

```bash
# Crear archivo de configuración
sudo nano /etc/nginx/sites-available/canciones
```

Agregar esta configuración:

```nginx
server {
    listen 80;
    server_name _;  # Acepta cualquier dominio o IP

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # Para WebSocket
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        
        # Timeouts
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }
}
```

```bash
# Habilitar la configuración
sudo ln -s /etc/nginx/sites-available/canciones /etc/nginx/sites-enabled/

# Eliminar configuración por defecto (opcional)
sudo rm /etc/nginx/sites-enabled/default

# Verificar configuración
sudo nginx -t

# Reiniciar Nginx
sudo systemctl restart nginx
```


### **8. Ejecutar la Aplicación**

**Opción A: Ejecución en primer plano (para pruebas)**

```bash
java -jar target/canciones-0.0.1-SNAPSHOT.jar
```


** Como servicio systemd (RECOMENDADO)**

```bash
# Crear archivo de servicio
sudo nano /etc/systemd/system/canciones.service
```

Agregar esta configuración:

```ini
[Unit]
Description=Canciones Spring Boot Application
After=syslog.target network.target mariadb.service

[Service]
User=deverlabschile
WorkingDirectory=/home/deverlabschile/canciones_backend
ExecStart=/usr/bin/java -jar /home/deverlabschile/canciones_backend/target/canciones-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
StandardOutput=journal
StandardError=journal
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
# Recargar systemd
sudo systemctl daemon-reload

# Iniciar servicio
sudo systemctl start canciones

# Habilitar inicio automático
sudo systemctl enable canciones

# Ver estado
sudo systemctl status canciones

# Ver logs
sudo journalctl -u canciones -f
```

### **9. Verificar el Despliegue**

Abre tu navegador y accede a:
- `http://TU_IP_PUBLICA`
- `http://TU_DOMINIO`

---

## 💻 Desarrollo Local (Windows)

### **Compilar el Proyecto**

```powershell
# Limpiar y empaquetar
.\mvnw.cmd clean package

# Sin ejecutar tests
.\mvnw.cmd clean package -DskipTests
```

### **Ejecutar la Aplicación**

```powershell
# Con Maven
.\mvnw.cmd spring-boot:run

# O con el JAR generado
java -jar target\canciones-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: `http://localhost:8080`

---




### **Actualizar la Aplicación**

```bash
# En el servidor
cd ~/canciones

# Detener la aplicación
sudo systemctl stop canciones

# Actualizar código (git pull o copiar nuevo JAR)
mvn clean package -DskipTests

# Iniciar la aplicación
sudo systemctl start canciones

# Ver logs para verificar
sudo journalctl -u canciones -f
```

---




