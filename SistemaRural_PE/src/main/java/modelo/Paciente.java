package modelo;

//2. Paciente.java
import java.util.List;

public class Paciente extends Persona {
 private String numeroHistoriaClinica;
 private String tipoSeguro;
 private HistorialMedico historialMedico = new HistorialMedico();
 //Getters and Setters
 public String getNumeroHistoriaClinica() {
	return numeroHistoriaClinica;
}


 public void setNumeroHistoriaClinica(String numeroHistoriaClinica) {
	this.numeroHistoriaClinica = numeroHistoriaClinica;
 }


 public String getTipoSeguro() {
	return tipoSeguro;
 }


 public void setTipoSeguro(String tipoSeguro) {
	this.tipoSeguro = tipoSeguro;
 }


 public HistorialMedico getHistorialMedico() {
	return historialMedico;
 }


 public List<CitaMedica> getCitasMedicas() {
	return historialMedico.getCitas();
 }

 
 
 

 //Clase solicitarCita con parámetro objeto de CitaMedica
 //Agregar objeto de clase a la Lista citasMedicas
 public void solicitarCita(CitaMedica cita) {
     historialMedico.agregarCita(cita);
 }

 
 //Agregar un método público consultarHistorial
 public List<CitaMedica> consultarHistorial() {
     return historialMedico.consultarAtenciones();
 }
}


