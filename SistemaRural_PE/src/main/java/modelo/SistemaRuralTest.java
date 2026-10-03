package modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SistemaRuralTest {

    @Test
    public void testEnmascaramientoDniLey29733() {
        // 1. Configuración (Arrange)
        Paciente paciente = new Paciente();
        paciente.setDni("74456153");

        // 2. Ejecución (Act)
        String dniProtegido = paciente.getDniEnmascarado();

        // 3. Verificación (Assert)
        assertEquals("****6153", dniProtegido, "El sistema debe ocultar los primeros 4 dígitos del DNI");
    }

    @Test
    public void testDescuentoAutomaticoDeStock() {
        // 1. Configuración
        Medicamento med = new Medicamento();
        med.setNombre("Paracetamol");
        med.setStockDisponible(100);
        med.setFechaVencimiento(LocalDate.now().plusYears(1));

        DetalleReceta detalle = new DetalleReceta();
        detalle.setMedicamento(med);
        detalle.setCantidad(20);

        AtencionMedica atencion = new AtencionMedica("A01", LocalDateTime.now(), "Fiebre", "Tomar pastilla");

        // 2. Ejecución
        atencion.agregarDetalleReceta(detalle);

        // 3. Verificación
        assertEquals(80, med.getStockDisponible(), "El stock del medicamento debe reducirse exactamente en la cantidad recetada");
    }

    @Test
    public void testCambioDeEstadoDeCitaATriaje() {
        // 1. Configuración
        CitaMedica cita = new CitaMedica();
        cita.programarCita(); 
        
        // Verificamos que inicie en PENDIENTE
        assertEquals(CitaMedica.EstadoCita.PENDIENTE, cita.getEstado());

        // 2. Ejecución
        cita.registrarAtencion();

        // 3. Verificación
        assertEquals(CitaMedica.EstadoCita.ATENDIDA, cita.getEstado(), "El estado de la cita debe cambiar a ATENDIDA al registrar la atención");
        assertNotNull(cita.getAtencionMedica(), "El sistema debe haber instanciado un objeto AtencionMedica");
    }
}