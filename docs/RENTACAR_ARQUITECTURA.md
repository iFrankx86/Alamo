# 🏢 Álamo Rent-A-Car — Arquitectura de Datos y MER

Este documento describe el Modelo Entidad-Relación (MER) lógico y físico implementado en el sistema consolidado de **Álamo Rent-A-Car**, detallando el diseño de tablas, mapeos de persistencia JPA y relaciones transaccionales.

---

## 📐 1. Modelo Entidad-Relación (MER Lógico)

El sistema de alquiler de vehículos gira en torno a la entidad central `tb_contrato_alquiler`, la cual actúa como nexo transaccional entre los clientes, la flota de vehículos, los seguros y los servicios adicionales.

```
       ┌────────────────────────┐         ┌────────────────────────┐
       │     tb_categoria       │         │       tb_usuario       │
       │  (Económico, Premium)  │         │  (Clientes y Staff)    │
       └───────────┬────────────┘         └───────────┬────────────┘
                   │ 1                                │ 1
                   │                                  │
                   │ N (Categoría)                    │ N (Cliente)
       ┌───────────▼────────────┐         ┌───────────▼────────────┐
       │      tb_vehiculo       │◄────────┤  tb_contrato_alquiler  ├────────┐
       │     (Flota / Placa)    │ 1     N │   (Núcleo Alquiler)    │        │
       └────────────────────────┘         └───────────┬────────────┘        │
                                                      │                     │
                                                      │ N                   │ N
                                          ┌───────────▼────────────┐        │
                                          │      tb_notificacion   │        │
                                          │     (Alertas de CRUD)  │        │
                                          └────────────────────────┘        │
                                                                            │
                                          ┌────────────────────────┐        │
                                          │       tb_servicio      │◄───────┘
                                          │   (GPS, Conductor)     │ N
                                          └────────────────────────┘
```

---

## 🗂️ 2. Mapeo de Tablas y Relaciones JPA

A continuación se detallan las tablas físicas generadas por la persistencia Hibernate/JPA:

### 👥 A. Tabla `tb_usuario`
Mapea tanto al personal interno de la empresa (Administradores y Counters) como a los clientes arrendatarios finales, diferenciados por su rol y tipo de licencia.
* **Campos:** `id_usuario` (PK), `nombres`, `apellido_paterno`, `apellido_materno`, `correo`, `clave`, `rol` (`ADMINISTRADOR`, `COUNTER`), `licencia` (Tipo A/B/C), `activo`.

### 🚗 B. Tabla `tb_vehiculo`
Representa el inventario de automóviles de la flota comercial de Álamo.
* **Campos:** `id_vehiculo` (PK), `placa` (Única), `marca`, `modelo`, `anio`, `id_categoria` (FK).
* **Relación:** `ManyToOne` con `tb_categoria` (Eager loading).

### 📋 C. Tabla `tb_contrato_alquiler`
Representa el eje de la transacción financiera y del servicio de renta del auto.
* **Campos:** `id_contrato` (PK), `codigo` (Único), `fecha_inicio`, `fecha_fin`, `monto_total`, `id_vehiculo` (FK), `id_cliente` (FK), `id_seguro` (FK), `id_horario` (FK), `estado` (Varchar, default `"ACTIVO"`).
* **Relaciones:**
  * `ManyToOne` hacia `tb_vehiculo` (Eager).
  * `ManyToOne` hacia `tb_usuario` (Cliente, Eager).
  * `ManyToMany` hacia `tb_servicio` (mediante tabla pivote `tb_contrato_alquiler_servicio`).

### 🙋‍♂️ D. Tabla `tb_soporte_ticket`
Registra los incidentes o requerimientos de asistencia técnica de los usuarios.
* **Campos:** `id_ticket` (PK), `asunto`, `descripcion`, `estado` (`ABIERTO`, `RESUELTO`), `fecha_creacion`.

### 🔔 E. Tabla `tb_notificacion`
Registra las alertas persistentes en la base de datos disparadas por eventos de sistema.
* **Campos:** `id_notificacion` (PK), `titulo`, `mensaje`, `leido` (Boolean), `tipo` (`CONTRATO`, `VEHICULO`, `USUARIO`, `SOPORTE`), `fecha_creacion`.

---

## 🔄 3. Reglas de Negocio en la Base de Datos

1. **Exclusión de Traslapes de Reserva:**
   Un vehículo no puede tener dos contratos activos cuyos periodos de fecha se intersecten. La query de validación se realiza mediante:
   ```sql
   c.fechaInicio <= :fechaFin AND c.fechaFin >= :fechaInicio
   ```
2. **Ciclo de Vida del Contrato (Soft-Delete):**
   Para mantener consistencia en las analíticas de facturación, la eliminación de contratos no elimina filas físicas de `tb_contrato_alquiler`, sino que cambia su estado a `"RESCINDIDO"`.
