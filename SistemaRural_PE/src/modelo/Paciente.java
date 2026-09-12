package modelo;

//2. Paciente.java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Paciente extends Persona {
 private String numeroHistoriaClinica;
 private String tipoSeguro;
 private List<CitaMedica> citasMedicas = new ArrayList<>(); 

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


 public List<CitaMedica> getCitasMedicas() {
	return citasMedicas;
 }


 public void setCitasMedicas(List<CitaMedica> citasMedicas) {
	this.citasMedicas = citasMedicas;
 }


 //Clase solicitarCita con parámetro objeto de CitaMedica
 //Agregar objeto de clase a la Lista citasMedicas
 public void solicitarCita(CitaMedica cita) {
     citasMedicas.add(cita);
 }

 
 //Agregar un método público consultarHistorial
 public List<CitaMedica> consultarHistorial() {
     // tomar la lista de citasMedicas y evaluarla con Stream
     return citasMedicas.stream()
    		 
    	// Considera solo las listas con estado ATENDIDA
         .filter(cita -> cita.getEstado() == CitaMedica.EstadoCita.ATENDIDA)
         
        // Colecciona los elemntos en la lista creada
         .collect(Collectors.toList());
 }
}


