
```markdown
# 🏥 Sistema Integral de Salud Rural - San Juan de Lurigancho

Sistema de gestión médica desarrollado en **Java Swing** para un centro de salud rural, que cubre el flujo completo desde la recepción del paciente hasta la emisión de recetas médicas en PDF, aplicando buenas prácticas de arquitectura empresarial y protección de datos personales.

---

## 📋 Descripción del Proyecto

Este sistema simula la operación de un centro médico rural, permitiendo:

- Registro de pacientes con validación simulada contra **RENIEC**.
- Programación y triaje de citas médicas.
- Atención médica con diagnóstico, tratamiento y emisión de recetas.
- Descuento automático de stock de medicamentos.
- Generación de recetas en **PDF** (iText).
- Consulta de historiales clínicos aplicando la **Ley N.º 29733** (Protección de Datos Personales).

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue el patrón **MVC (Modelo - Vista - Controlador)** organizado en dos paquetes principales:

```
src/
├── modelo/
│   ├── Persona.java            (Clase abstracta base)
│   ├── Paciente.java           (Extiende Persona)
│   ├── Medico.java             (Extiende Persona)
│   ├── CitaMedica.java         (Con enum EstadoCita)
│   ├── AtencionMedica.java     (Diagnóstico y tratamiento)
│   ├── DetalleReceta.java      (Vínculo Medicamento-Cantidad)
│   ├── Medicamento.java        (Stock y vencimiento)
│   ├── HistorialMedico.java    (Colección de citas)
│   └── Ejecucion.java          (Clase de prueba por consola)
│
└── vista/
    ├── FrmPrincipal.java       (Dashboard principal)
    ├── FrmRegistro.java        (Recepción y triaje)
    ├── FrmAtencion.java        (Consultorio médico + PDF)
    ├── FrmConsulta.java        (Archivo clínico)
    └── FrmSistema.java         (Simulación demostrativa)
```

---

## 🧩 Módulos del Sistema

### 1️⃣ Recepción y Triaje (`FrmRegistro`)
- Conexión simulada con **API RENIEC** para autocompletar datos.
- Validación de DNI, nombres y fecha de nacimiento.
- Registro de nuevos pacientes y generación de número de historia clínica.
- Asignación de especialidad y médico de preferencia.
- Registro del motivo de consulta (triaje).

### 2️⃣ Consultorio Médico (`FrmAtencion`)
- Búsqueda de paciente por DNI.
- Detección automática de citas pendientes.
- Registro de diagnóstico y tratamiento.
- Prescripción de medicamentos con validación de stock.
- **Generación de receta médica en PDF** con firma y sello del médico.

### 3️⃣ Archivo Clínico (`FrmConsulta`)
- Búsqueda de historial clínico por DNI.
- Visualización de ficha técnica con **DNI enmascarado** (Ley N.º 29733).
- Gestión de estados de citas (Pendiente / Finalizada / Cancelada).
- Registro completo de atenciones y derivaciones.

### 4️⃣ Simulación (`FrmSistema`)
- Ejecución demostrativa del flujo completo del sistema sin interacción con formularios.

---

## 🔐 Cumplimiento Legal

El sistema implementa el **enmascaramiento parcial del DNI** (`****1234`) en las vistas de consulta, conforme a la **Ley N.º 29733 – Ley de Protección de Datos Personales del Perú**.

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Uso |
|------------|-----|
| **Java SE** | Lenguaje principal |
| **Java Swing** | Interfaz gráfica de usuario |
| **iText PDF** | Generación de recetas en PDF |
| **java.time** | Manejo de fechas (LocalDate / LocalDateTime) |
| **Streams API** | Filtrado y procesamiento de colecciones |
| **Programación Orientada a Objetos** | Herencia, encapsulamiento, polimorfismo |

---

## ⚙️ Requisitos

- **JDK 8** o superior.
- **iText 5.x** (`itextpdf-5.x.x.jar`) en el classpath para la generación de PDF.
- IDE recomendado: **Eclipse**, **IntelliJ IDEA** o **NetBeans**.

---

## 🚀 Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/usuario/sistema-salud-rural.git
   ```

2. Importa el proyecto en tu IDE favorito.

3. Asegúrate de tener el JAR de **iText** agregado al *Build Path*.

4. Ejecuta la clase principal:
   ```
   vista/FrmPrincipal.java
   ```

5. Alternativamente, puedes ejecutar la simulación por consola:
   ```
   modelo/Ejecucion.java
   ```

---

## 🧪 Datos de Prueba

El sistema incluye datos precargados para pruebas:

**Pacientes demo con citas:**
| DNI | Paciente | Estado |
|-----|----------|--------|
| 45721839 | Luis Miguel Rojas Cárdenas | Cita pendiente en Cardiología |
| 71239485 | Carmen Sofía Chávez Ramírez | Cita pendiente en Medicina General |
| 09458123 | Julio César Flores Huamán | Historial de Traumatología |
| 60192837 | Daniela Andrea Pérez Castillo | Cita pendiente |
| 42857193 | Roberto Carlos Gutiérrez Quispe | Historial de Cardiología |

**DNIs válidos para simular API RENIEC:**
`74456153`, `75836620`, `70294511`

---

## 📸 Capturas del Sistema

> *(Sección sugerida para agregar screenshots de los formularios)*

- Dashboard Principal
- Módulo de Recepción y Triaje
- Módulo de Consultorio Médico
- Módulo de Archivo Clínico
- Receta PDF generada

---

## 👨‍💻 Autores

- **Graciela Liz Ruiz Ramos**
- **Alejandro Miguel Huilcaya Dominguez**
- **Aaron Keneth Gonzales Cortez**

---

## 📄 Licencia

Este proyecto fue desarrollado con fines académicos para el curso de **Arquitectura Empresarial**.

---

## 📌 Estado del Proyecto

✅ Funcional – En fase de documentación y presentación final.

---

> **Sistema Rural-PE © 2026** | Centro Médico – San Juan de Lurigancho
```

---

