from datetime import datetime, date, timedelta
from Paciente import Paciente
from Medico import Medico
from Cita_Medica import CitaMedica
from Medicamento import Medicamento
from DetalleReceta import DetalleReceta

def main():
    # ==========================================
    # 1. REGISTRO DE DATOS MAESTROS (PACIENTES, MÉDICOS, MEDICAMENTOS)
    # ==========================================
    
    # Pacientes
    paciente1 = Paciente()
    paciente1.id_persona = "P001"
    paciente1.dni = "45678912"
    paciente1.nombres = "Rosa"
    paciente1.apellidos = "Quispe Mamani"
    paciente1.numero_historia_clinica = "HC-001"
    paciente1.tipo_seguro = "SIS"

    paciente2 = Paciente()
    paciente2.id_persona = "P002"
    paciente2.dni = "76543210"
    paciente2.nombres = "Juan Carlos"
    paciente2.apellidos = "Pérez Gómez"
    paciente2.numero_historia_clinica = "HC-002"
    paciente2.tipo_seguro = "EsSalud"

    # Médicos
    medico1 = Medico()
    medico1.id_persona = "M001"
    medico1.dni = "41234567"
    medico1.nombres = "Carlos"
    medico1.apellidos = "Torres Vega"
    medico1.cmp = "CMP-55210"
    medico1.especialidad = "Medicina General"

    medico2 = Medico()
    medico2.id_persona = "M002"
    medico2.dni = "44556677"
    medico2.nombres = "María"
    medico2.apellidos = "Salazar Ruiz"
    medico2.cmp = "CMP-66321"
    medico2.especialidad = "Gastroenterología"

    # Medicamentos
    med_omeprazol = Medicamento()
    med_omeprazol.id_medicamento = "MED001"
    med_omeprazol.nombre = "Omeprazol 20mg"
    med_omeprazol.stock_disponible = 50
    med_omeprazol.fecha_vencimiento = date.today() + timedelta(days=365)

    med_paracetamol = Medicamento()
    med_paracetamol.id_medicamento = "MED002"
    med_paracetamol.nombre = "Paracetamol 500mg"
    med_paracetamol.stock_disponible = 100
    med_paracetamol.fecha_vencimiento = date.today() + timedelta(days=500)

    # ==========================================
    # 2. SIMULACIÓN DE FLUJOS (CITAS Y ATENCIONES)
    # ==========================================

    # --- Cita 1: Rosa (Atendida por Carlos) ---
    cita1 = CitaMedica()
    cita1.id_cita = "C001"
    cita1.fecha_hora = datetime.now() - timedelta(days=2) # Hace 2 días
    cita1.motivo_consulta = "Dolor abdominal agudo"
    cita1.programar_cita()

    paciente1.solicitar_cita(cita1)
    medico1.citas_asignadas.append(cita1)

    medico1.atender_cita(cita1)
    atencion1 = cita1.atencion_medica
    atencion1.diagnostico = "Gastritis aguda"
    atencion1.tratamiento = "Dieta blanda estricta"

    detalle1 = DetalleReceta()
    detalle1.medicamento = med_omeprazol
    detalle1.cantidad = 14
    detalle1.indicaciones = "Tomar 1 cápsula en ayunas por 14 días"
    medico1.emitir_receta(atencion1, detalle1)

    # --- Cita 2: Juan Carlos (Atendido por Carlos) ---
    cita2 = CitaMedica()
    cita2.id_cita = "C002"
    cita2.fecha_hora = datetime.now() - timedelta(hours=5) # Hace 5 horas
    cita2.motivo_consulta = "Fiebre y malestar general"
    cita2.programar_cita()

    paciente2.solicitar_cita(cita2)
    medico1.citas_asignadas.append(cita2)

    medico1.atender_cita(cita2)
    atencion2 = cita2.atencion_medica
    atencion2.diagnostico = "Infección viral"
    atencion2.tratamiento = "Reposo absoluto y mucha hidratación"

    detalle2 = DetalleReceta()
    detalle2.medicamento = med_paracetamol
    detalle2.cantidad = 10
    detalle2.indicaciones = "Tomar 1 pastilla cada 8 horas si hay fiebre"
    medico1.emitir_receta(atencion2, detalle2)

    # --- Cita 3: Rosa (Cita Futura/Pendiente con la Especialista María) ---
    cita3 = CitaMedica()
    cita3.id_cita = "C003"
    cita3.fecha_hora = datetime.now() + timedelta(days=5) # En 5 días
    cita3.motivo_consulta = "Control de gastritis por derivación"
    cita3.programar_cita()

    paciente1.solicitar_cita(cita3)
    medico2.citas_asignadas.append(cita3)
    # Nota: El médico 2 aún NO atiende esta cita, se queda en estado PENDIENTE.

    # ==========================================
    # 3. IMPRESIÓN DE RESULTADOS
    # ==========================================

    print("\n" + "="*60)
    print(" 🏥 REPORTE GENERAL DEL CENTRO DE SALUD")
    print("="*60)

    # Reporte de Paciente 1 (Rosa)
    print(f"\n👤 PACIENTE: {paciente1.get_nombre_completo()} (DNI: {paciente1.get_dni_enmascarado()}) - {paciente1.tipo_seguro}")
    print(f"Total de citas registradas: {len(paciente1.get_citas_medicas())}")
    print(f"Citas efectivas (Atendidas): {len(paciente1.consultar_historial())}")
    for c in paciente1.get_citas_medicas():
        print(f"  - [{c.estado.value}] Cita ID: {c.id_cita} | Fecha: {c.fecha_hora.strftime('%d/%m/%Y')} | Motivo: {c.motivo_consulta}")
        if c.atencion_medica:
            print(f"    ↳ Informe: {c.atencion_medica.generar_informe()}")

    # Reporte de Paciente 2 (Juan)
    print(f"\n👤 PACIENTE: {paciente2.get_nombre_completo()} (DNI: {paciente2.get_dni_enmascarado()}) - {paciente2.tipo_seguro}")
    print(f"Total de citas registradas: {len(paciente2.get_citas_medicas())}")
    print(f"Citas efectivas (Atendidas): {len(paciente2.consultar_historial())}")
    for c in paciente2.get_citas_medicas():
        print(f"  - [{c.estado.value}] Cita ID: {c.id_cita} | Fecha: {c.fecha_hora.strftime('%d/%m/%Y')} | Motivo: {c.motivo_consulta}")
        if c.atencion_medica:
            print(f"    ↳ Informe: {c.atencion_medica.generar_informe()}")

    print("\n" + "-"*60)
    print(" 👨‍⚕️ CARGA DE TRABAJO MÉDICA")
    print("-"*60)
    print(f"Dr(a). {medico1.get_nombre_completo()} ({medico1.especialidad}) -> {len(medico1.citas_asignadas)} citas asignadas.")
    print(f"Dr(a). {medico2.get_nombre_completo()} ({medico2.especialidad}) -> {len(medico2.citas_asignadas)} citas asignadas.")

    print("\n" + "-"*60)
    print(" 💊 INVENTARIO DE FARMACIA ACTUALIZADO")
    print("-"*60)
    print(f"- {med_omeprazol.nombre}: {med_omeprazol.stock_disponible} unidades restantes.")
    print(f"- {med_paracetamol.nombre}: {med_paracetamol.stock_disponible} unidades restantes.")
    print("="*60 + "\n")

if __name__ == "__main__":
    main()