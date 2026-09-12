package modelo;

import java.util.ArrayList;
import java.util.List;

// Clase que representa a un profesional de la salud en el sistema rural.
// Aplica el principio de HERENCIA al extender de la clase abstracta Persona.

public class Medico extends Persona {
	
	private String cmp;// Registro del Colegio Médico del Perú
	private String especialidad;
	private List<CitaMedica> citasAsignadas = new ArrayList<>(); // 1..* CitaMedica

	//Procesa una cita médica pendiente y genera su respectiva atención.
	public void atenderCita(CitaMedica cita) {
     cita.registrarAtencion();
 }

	//Vincula un medicamento recetado a una atención médica específica.
	public void emitirReceta(AtencionMedica atencion, DetalleReceta detalle) {
     atencion.agregarDetalleReceta(detalle);
 }
}