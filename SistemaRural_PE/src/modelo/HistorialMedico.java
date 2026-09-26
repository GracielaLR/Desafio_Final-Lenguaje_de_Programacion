package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HistorialMedico {

    private List<CitaMedica> citas = new ArrayList<>();

    // Agrega una nueva cita al historial del paciente
    public void agregarCita(CitaMedica cita) {
        citas.add(cita);
    }

    // Retorna la lista completa de citas (sin filtrar)
    public List<CitaMedica> getCitas() {
        return citas;
    }

    // Retorna solo las citas cuyo estado es ATENDIDA
    public List<CitaMedica> consultarAtenciones() {
        return citas.stream()
                .filter(cita -> cita.getEstado() == CitaMedica.EstadoCita.ATENDIDA)
                .collect(Collectors.toList());
    }
}