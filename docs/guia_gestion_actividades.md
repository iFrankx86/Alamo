# Guía de Gestión de Actividades, Seguimiento de Incidentes y Calidad de Código

Esta guía contiene la fundamentación teórica para tu informe académico y las plantillas exactas que debes copiar y pegar en **GitHub Issues** y **GitHub Projects** (o Trello) para documentar el trabajo de forma altamente profesional.

---

## 1. Fundamentación Teórica (Para el Informe)

### Importancia de los Pull Requests (PR) en la Calidad del Código
Un Pull Request no es simplemente una petición para fusionar ramas; es una de las herramientas de control de calidad más potentes en la ingeniería de software moderna por las siguientes razones:
1. **Revisión de Código (Code Review):** Permite que otros miembros del equipo examinen el código línea por línea para encontrar bugs, redundancias o fallos de lógica antes de que afecten a los usuarios.
2. **Puerta de Enlace para CI/CD:** El PR actúa como un desencadenador para que los pipelines automatizados (como el que creamos con GitHub Actions) compilen y prueben el código automáticamente. Si el compilador falla, el PR bloquea la fusión, protegiendo la rama estable (`main`).
3. **Compartir Conocimiento:** Ayuda a alinear al equipo sobre la arquitectura que se está implementando y mantiene a todos al tanto de las decisiones de diseño del software.
4. **Historial Documentado:** Deja un rastro de por qué se hizo un cambio, qué problema solucionó y quién lo aprobó, facilitando el mantenimiento a largo plazo.

### Relación entre Tareas, Issues, Commits y PRs
El ciclo de vida del software sigue una trazabilidad estricta:
1. Una tarea se define en el **Tablero de Gestión (Kanban)**.
2. Si la tarea es un error o mejora, se registra como un **GitHub Issue** (con un número identificador único, ej: `#12`).
3. El desarrollador crea una rama específica para solucionar ese issue. Cada **Commit** de código describe una acción del avance.
4. Se abre un **Pull Request (PR)** que vincula formalmente la rama del cambio con la rama principal. En el PR se usa la palabra clave `Fixes #12` o `Closes #12`. Al fusionarse el PR, GitHub cierra automáticamente el Issue #12 y mueve la tarea en el Tablero a **"Done"**.

---

## 2. Plantillas de GitHub Issues para Crear en tu Repositorio

Ve a la pestaña **Issues** de tu repositorio en GitHub, haz clic en **New Issue** y crea estos tres issues copiando y pegando el texto:

### 🐛 Issue 1: Reporte de Bug (Incidente Resuelto)
* **Título:** `bug: Error de serialización (Internal Server Error 500) en peticiones GET y PUT`
* **Cuerpo (Markdown):**
```markdown
### Descripción del Problema
Al listar o editar registros en la interfaz (Usuarios y Vehículos), el servidor Spring Boot responde con un error HTTP 500.

### Causa Raíz
1. **GET:** Jackson intenta serializar relaciones marcadas como `FetchType.LAZY` (perezosas), topándose con el proxy de Hibernate (`ByteBuddyInterceptor`), el cual no tiene un serializador registrado.
2. **PUT:** La petición de edición del frontend no está incluyendo el campo ID del registro en el cuerpo (payload) JSON. Hibernate interpreta el ID nulo como una nueva entidad e intenta realizar un `INSERT` en vez de un `UPDATE`, violando restricciones de claves únicas de la base de datos.

### Pasos para la Solución
- [x] Cambiar carga de relaciones clave a `FetchType.EAGER` en las entidades `Usuario`, `Vehiculo` y `ContratoAlquiler`.
- [x] Modificar los formularios de edición en el frontend para enviar explícitamente `idUsuario` e `idVehiculo` en la petición `PUT`.
- [x] Reconstruir contenedores en Docker y verificar localmente.
```

---

### 🚀 Issue 2: Requerimiento de Mejora (Completado)
* **Título:** `fature: Rediseño ceompleto del frontend en React y panel Dashboard interactivo`
* **Cuerpo (Markdown):**
```markdown
### Descripción
Es necesario construir una interfaz de usuario profesional y moderna en React (Vite + TypeScript) que se conecte a las APIs REST expuestas por el backend de Spring Boot, reemplazando la estructura anterior del repositorio que se encontraba dañada.

### Requerimientos de la Interfaz
- [x] Diseñar un Dashboard interactivo con KPIs de usuarios, flota de vehículos, contratos y ganancias.
- [x] Crear un layout responsivo con Sidebar lateral y Navbar superior con widget de notificaciones interactivo.
- [x] Implementar CRUDs completos con modales de creación/edición para los módulos de **Usuarios**, **Vehículos** y **Contratos**.
- [x] Diseñar una pestaña de **Configuración** con opción de sembrado de base de datos automático (Seeds).
- [x] Aplicar estilos premium usando variables CSS personalizadas con efectos Glassmorphic y micro-animaciones.
```

---

### 🔧 Issue 3: DevOps e Infraestructura (Completado)
* **Título:** `chore: Configuración de pipeline de CI/CD con GitHub Actions y Vercel`
* **Cuerpo (Markdown):**
```markdown
### Objetivo
Automatizar las fases de construcción, prueba e integración del software para asegurar la estabilidad del repositorio y habilitar la entrega continua (CD) en la nube.

### Tareas
- [x] Crear el workflow de integración continua en `.github/workflows/ci.yml`.
- [x] Configurar compulación automatizada del Backend Java (Spring Boot) con JDK 21 y Maven.
- [x] Configurar compilación automatizada del Frontend React con Node.js 20.
- [x] Configurar la redirección del enrutamiento de React en la nube mediante `vercel.json`.
- [x] Integrar despliegue automático del Frontend a Vercel ante fusiones en `main`.
```

---

## 3. Configuración de Milestones (Hitos del Proyecto)

Los **Milestones** agrupan los issues y pull requests para dar un seguimiento porcentual a las metas de entrega (versiones del software). Crea los siguientes hitos en la sección **Issues ➡️ Milestones** de tu GitHub:

### 🏁 Hito 1: `v1.0.0-beta (Core & API Restoration)`
* **Estado:** **Cerrado** (100% completado).
* **Descripción:** Restauración de la arquitectura del backend, corrección de entidades JPA y configuración inicial de la base de datos PostgreSQL.
* **Issues asociados:** Issue #1 (Bug de serialización).

### 🏁 Hito 2: `v1.0.0-release (React Frontend & CI/CD)`
* **Estado:** **Cerrado** (100% completado).
* **Descripción:** Implementación de la UI premium en React Vite, dashboard de control y automatización de pipelines de CI/CD en Vercel y Render.
* **Issues asociados:** Issue #2 (Frontend en React) e Issue #3 (CI/CD Pipeline).

### 🏁 Hito 3: `v1.1.0 (Testing & Reporting Features)`
* **Estado:** **Abierto** (Por hacer - 0% completado).
* **Descripción:** Incorporación de exportación de reportes de alquiler en formato PDF y ampliación de cobertura de pruebas automatizadas en el backend.
* **Issues asociados (A crear a futuro):** Tarea de JUnit y Tarea de reportes PDF.

---

## 4. Organización de GitHub Projects o Trello (Tablero Kanban)

Crea un **GitHub Project** en la pestaña "Projects" de tu repositorio o un tablero en **Trello** con las siguientes columnas y tarjetas:

| Columnas del Tablero | Tarjetas (Cards) a incluir | Asignado a | Estado | Hito (Milestone) |
| :--- | :--- | :--- | :--- | :--- |
| **Backlog / Por Hacer** | - Configurar pruebas unitarias con JUnit.<br>- Agregar exportación de reportes PDF. | (Sin asignar) | Por Hacer | `v1.1.0 (Testing & Reporting)` |
| **En Progreso (In Progress)** | - Implementación de pruebas automatizadas CI/CD. | FrancoGPU | En Progreso | `v1.0.0-release` |
| **Hecho (Done)** | - **Issue #1:** bug: Error de serialización.<br>- **Issue #2:** feature: Rediseño React.<br>- **Issue #3:** chore: Configuración de CI/CD. | FrancoGPU | Hecho | `v1.0.0-beta` / `v1.0.0-release` |

### 📸 Captura recomendada para el informe:
Una vez que vincules los issues a sus respectivos Milestones y los cierres, GitHub te mostrará una barra de progreso al **100% completado** para los hitos `v1.0.0-beta` y `v1.0.0-release`, y al **0%** para `v1.1.0`. Toma una captura de pantalla de la pestaña **Milestones** en GitHub; servirá como evidencia perfecta de la planificación y release-management del proyecto.
