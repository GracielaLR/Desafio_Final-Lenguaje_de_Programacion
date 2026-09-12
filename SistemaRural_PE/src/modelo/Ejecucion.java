package modelo;
import modelo.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ejecucion {

    public static void main(String[] args) {

        // 1. Registrar paciente
        Paciente paciente = new Paciente();
        paciente.setIdPersona("P001");
        paciente.setDni("45678912");
        paciente.setNombres("Rosa");
        paciente.setApellidos("Quispe Mamani");
        paciente.setNumeroHistoriaClinica("HC-001");
        paciente.setTipoSeguro("SIS");

        // 2. Registrar médico
        Medico medico = new Medico();
        medico.setIdPersona("M001");
        medico.setDni("41234567");
        medico.setNombres("Carlos");
        medico.setApellidos("Torres Vega");
        medico.setCmp("CMP-55210");
        medico.setEspecialidad("Medicina General");

        // 3. Paciente solicita una cita
        CitaMedica cita = new CitaMedica();
        cita.setIdCita("C001");
        cita.setFechaHora(LocalDateTime.now());
        cita.setMotivoConsulta("Dolor abdominal");
        cita.programarCita();

        paciente.solicitarCita(cita);
        medico.getCitasAsignadas().add(cita);

        // 4. Médico atiende la cita y registra la atención
        medico.atenderCita(cita);
        AtencionMedica atencion = cita.getAtencionMedica();
        atencion.setDiagnostico("Gastritis leve");
        atencion.setTratamiento("Dieta blanda y medicación");

        // 5. Registrar medicamento disponible en almacén
        Medicamento medicamento = new Medicamento();
        medicamento.setIdMedicamento("MED001");
        medicamento.setNombre("Omeprazol 20mg");
        medicamento.setStockDisponible(50);
        medicamento.setFechaVencimiento(LocalDate.now().plusYears(1));

        // 6. Médico emite receta con el medicamento
        DetalleReceta detalle = new DetalleReceta();
        detalle.setMedicamento(medicamento);
        detalle.setCantidad(10);
        detalle.setIndicaciones("Tomar 1 tableta cada 24 horas, en ayunas");

        medico.emitirReceta(atencion, detalle);

        // 7. Mostrar resultados
        System.out.println("Paciente: " + paciente.getNombreCompleto());
        System.out.println("DNI enmascarado: " + paciente.getDniEnmascarado());
        System.out.println("Médico tratante: " + medico.getNombreCompleto() + " (" + medico.getEspecialidad() + ")");
        System.out.println("Estado de la cita: " + cita.getEstado());
        System.out.println(atencion.generarInforme());
        System.out.println("Stock restante de " + medicamento.getNombre() + ": " + medicamento.getStockDisponible());

        // 8. Consultar historial de citas atendidas del paciente
        System.out.println("Historial de citas atendidas: " + paciente.consultarHistorial().size());
    }
}
