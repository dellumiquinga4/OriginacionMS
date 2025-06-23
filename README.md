# 📘 API REST - Sistema de Gestión Vehicular

## 🚗 Identificadores de Vehículos

### 🔹 Listar todos los identificadores

```
GET http://localhost:8080/v1/identificadores-vehiculos
```

### 🔹 Buscar por ID

```
GET http://localhost:8080/v1/identificadores-vehiculos/{id}
```

### 🔹 Crear un identificador

```
POST http://localhost:8080/v1/identificadores-vehiculos
```

```json
{
  "vin": "1HGCM82633A004352",
  "numeroMotor": "MTR123456789",
  "placa": "ABC1234"
}
```

---

## 🏢 Concesionarios

### 🔹 Listar todos

```
GET http://localhost:8080/v1/concesionarios
```

### 🔹 Buscar por ID

```
GET http://localhost:8080/v1/concesionarios/{id}
```

### 🔹 Buscar por estado

```
GET http://localhost:8080/v1/concesionarios/estado/{estado}
```

### 🔹 Buscar por razón social

```
GET http://localhost:8080/v1/concesionarios/razon-social/{razon}
```

### 🔹 Buscar por email

```
GET http://localhost:8080/v1/concesionarios/email/{email}
```

### 🔹 Crear concesionario

```
POST http://localhost:8080/v1/concesionarios
```

```json
{
  "razonSocial": "Concesionaria Ejemplo",
  "direccion": "Av. Principal 123",
  "telefono": "0999999999",
  "emailContacto": "contacto@ejemplo.com",
  "estado": "ACTIVO"
}
```

### 🔹 Actualizar concesionario

```
PUT http://localhost:8080/v1/concesionarios/{id}
```

```json
{
  "razonSocial": "Concesionaria Ejemplo",
  "direccion": "Av. Principal 123",
  "telefono": "0999999999",
  "emailContacto": "contacto@ejemplo.com",
  "estado": "ACTIVO"
}
```

> 📌 **Nota:** Cambiar el campo que se desea actualizar.

### 🔹 Desactivar concesionario

```
PATCH http://localhost:8080/v1/concesionarios/{id}/desactivar
```

---

## 🚘 Vehículos

### 🔹 Listar todos

```
GET http://localhost:8080/v1/vehiculos
```

### 🔹 Buscar por ID

```
GET http://localhost:8080/v1/vehiculos/{id}
```

### 🔹 Buscar por concesionario

```
GET http://localhost:8080/v1/vehiculos/concesionario/{idConcesionario}
```

### 🔹 Buscar por estado

```
GET http://localhost:8080/v1/vehiculos/estado/{estado}
```

### 🔹 Buscar por marca

```
GET http://localhost:8080/v1/vehiculos/marca/{marca}
```

### 🔹 Buscar por modelo

```
GET http://localhost:8080/v1/vehiculos/modelo/{modelo}
```

### 🔹 Buscar por identificador

```
GET http://localhost:8080/v1/vehiculos/identificador/{idIdentificador}
```

### 🔹 Crear vehículo

```
POST http://localhost:8080/v1/vehiculos
```

```json
{
  "idConcesionario": 2,
  "idIdentificadorVehiculo": 2,
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2022,
  "valor": 18000.00,
  "color": "Rojo",
  "extras": "Aire acondicionado, Bluetooth",
  "estado": "NUEVO"
}
```

### 🔹 Actualizar vehículo

```
PUT http://localhost:8080/v1/vehiculos/{id}
```

```json
{
  "idConcesionario": 2,
  "idIdentificadorVehiculo": 2,
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2022,
  "valor": 18000.00,
  "color": "Rojo",
  "extras": "Aire acondicionado, Bluetooth",
  "estado": "NUEVO"
}
```

> 📌 **Nota:** Cambiar el campo que se desea actualizar.

### 🔹 Eliminar vehículo

```
DELETE http://localhost:8080/v1/vehiculos/{id}
```

---

## 🧑‍💼 Vendedores

### 🔹 Listar todos

```
GET http://localhost:8080/v1/vendedores
```

### 🔹 Buscar por ID

```
GET http://localhost:8080/v1/vendedores/{id}
```

### 🔹 Buscar por email

```
GET http://localhost:8080/v1/vendedores/email/{email}
```

### 🔹 Buscar por concesionario

```
GET http://localhost:8080/v1/vendedores/concesionario/{idConcesionario}
```

### 🔹 Buscar por estado

```
GET http://localhost:8080/v1/vendedores/estado/{estado}
```

### 🔹 Crear vendedor

```
POST http://localhost:8080/v1/vendedores
```

```json
{
  "idConcesionario": 2,
  "nombre": "Juan Pérez",
  "telefono": "0988888888",
  "email": "juan.perez@ejemplo.com",
  "estado": "ACTIVO"
}
```

### 🔹 Actualizar vendedor

```
PUT http://localhost:8080/v1/vendedores/{id}
```

```json
{
  "idConcesionario": 2,
  "nombre": "Juan Pérez",
  "telefono": "0988888888",
  "email": "juan.perez@ejemplo.com",
  "estado": "ACTIVO"
}
```

> 📌 **Nota:** Cambiar el campo que se desea actualizar.

### 🔹 Desactivar vendedor

```
PATCH http://localhost:8080/v1/vendedores/{id}/desactivar
```

---

## 📋 Auditorías

### 🔹 Listar todas

```
GET http://localhost:8080/v1/auditorias
```

### 🔹 Buscar por ID

```
GET http://localhost:8080/v1/auditorias/{id}
```

### 🔹 Buscar por tabla

```
GET http://localhost:8080/v1/auditorias/tabla/{tabla}
```

### 🔹 Buscar por acción

```
GET http://localhost:8080/v1/auditorias/accion/{accion}
```

### 🔹 Buscar por rango de fechas

```
GET http://localhost:8080/v1/auditorias/fechas?desde=YYYY-MM-DDTHH:MM:SS&hasta=YYYY-MM-DDTHH:MM:SS
```

#### 📌 Ejemplo:

```
GET http://localhost:8080/v1/auditorias/fechas?desde=2025-06-22T21:40:00&hasta=2025-06-22T22:00:00
```

### 🔹 Crear auditoría

```
POST http://localhost:8080/v1/auditorias
```

```json
{
  "tabla": "vehiculos",
  "accion": "INSERT",
  "fechaHora": "2024-06-01T12:00:00"
}
```
