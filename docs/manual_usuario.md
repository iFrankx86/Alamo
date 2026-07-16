# 🏢 Álamo Rent-A-Car — Manual de Usuario

Bienvenido al manual operativo de la plataforma de gestión de alquileres **Álamo Rent-A-Car**. Aquí se detalla el funcionamiento de cada uno de los módulos de la aplicación y cómo interactuar con ellos para una correcta administración del negocio.

---

## 🔑 1. Acceso al Sistema y Roles

El sistema cuenta con un control de accesos basado en dos perfiles operativos:

1. **Administrador del Sistema:**
   * **Usuario:** `admin` (la contraseña puede ser cualquier texto).
   * **Permisos:** Acceso ilimitado a todas las secciones: *Dashboard*, *Usuarios*, *Vehículos*, *Contratos*, *Configuración* y *Soporte*. Puede ver y resolver tickets de soporte, y purgar tickets resueltos.
2. **Agente de Counter:**
   * **Usuario:** Cualquier texto distinto de admin (ej. `juan` o `maria`).
   * **Permisos:** Gestión de vehículos, creación y edición de contratos de alquiler, y envío de tickets de ayuda técnica. La sección de **Usuarios** permanece oculta por confidencialidad.

---

## 📊 2. Panel de Control y Analíticas Interactivas

Al ingresar al sistema, el **Dashboard** muestra métricas clave sincronizadas en tiempo real:
* **KPIs Directos:** Total de colaboradores activos, flota registrada, contratos de alquiler y facturación bruta estimada.
* **Área Chart (Facturación Mensual):** Gráfico interactivo que muestra las tendencias de ingresos mensuales distribuidos a lo largo del año.
* **Bar Chart (Flota por Categoría):** Histograma dinámico que muestra la cantidad de vehículos de tipo *Económico*, *Estándar* y *Premium*.
* **Actividad Reciente:** Tabla en la parte inferior con las últimas transacciones registradas.

---

## 👥 3. Gestión de Colaboradores (Usuarios)

*(Exclusivo para el rol **Administrador**)*
* **Visualización:** Lista de empleados con su nombre, correo, rol e insignia del tipo de licencia de conducir.
* **Filtros:** Barra de búsqueda para filtrar instantáneamente por nombre o correo.
* **CRUD:** Botones para registrar nuevos colaboradores y editar datos existentes.
* **Exportación:** Botones dedicados para descargar el listado completo en formato **Excel** o **PDF**.

---

## 🚗 4. Gestión de Flota (Vehículos)

* **Catálogo:** Muestra las placas (patentes únicas), marca, modelo, año y categoría del vehículo.
* **Categorías y Tarifas:**
  * **Económico (S/. 50.00/día):** Sedanes y hatchbacks pequeños.
  * **Estándar (S/. 95.00/día):** Sedanes medianos y SUVs compactas.
  * **Premium (S/. 175.00/día):** Camionetas de lujo y vehículos de gama alta.
* **CRUD & Exportación:** Permite añadir nuevos autos a la flota, editar sus especificaciones y exportar las listas a Excel/PDF.

---

## 📝 5. Módulo de Contratos de Alquiler

Es el núcleo operativo del negocio. Permite registrar las salidas de autos alquilados:
* **Filtros Avanzados:** Incluye una barra de búsqueda inteligente (por código, cliente o placa) y un dropdown selector para filtrar contratos por **Categoría del Vehículo** (Económico, Estándar, Premium).
* **Formulario de Alquiler:**
  * Al ingresar las fechas de entrega y devolución, el formulario ejecuta un **filtro de disponibilidad reactivo**. Solo se listarán en el dropdown los vehículos libres en ese rango, excluyendo los que tengan un contrato activo.
  * Permite añadir coberturas de seguro adicionales (S/. 25.00/día) y extras (GPS, conductor adicional, etc.).
  * **Calculadora Reactiva:** Muestra en tiempo real el monto total estimado del alquiler mientras cambias las opciones del formulario.
* **Visualización de Estados (Ciclo de Vida):**
  * **Activo (Insignia verde):** Contrato vigente.
  * **Rescindido (Insignia roja):** Contrato cancelado.
* **Edición y Cancelación:**
  * Al hacer clic en el ícono de lápiz o en "Editar Contrato" en la vista detallada, podrás modificar fechas, seguros o servicios del alquiler.
  * Al hacer clic en el ícono de papelera, el contrato se marca como **Rescindido** (Soft-Delete) y sus botones de acción quedan deshabilitados de forma segura.

---

## 🔔 6. Sistema de Notificaciones

Ubicado en la parte superior derecha del Navbar (campana con contador):
* Registra automáticamente una alerta cuando se crea, edita o elimina/rescinde un usuario, vehículo, contrato o ticket de soporte.
* Permite leer detalles, marcar notificaciones individuales como leídas o utilizar el botón **"Marcar todas como leídas"** para limpiar la bandeja.

---

## 🙋‍♂️ 7. Mesa de Soporte Técnico (Tickets de Ayuda)

* **Agente de Counter:** Puede reportar incidencias escribiendo un asunto y mensaje. El ticket se guarda como `ABIERTO`.
* **Administrador:** Panel de incidentes. Permite leer la consulta, cambiar el estado a `RESUELTO` cuando se solucione, o eliminar de forma definitiva los tickets resueltos utilizando el botón de papelera.
