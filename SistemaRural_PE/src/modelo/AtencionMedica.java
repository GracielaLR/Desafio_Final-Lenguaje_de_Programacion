package modelo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtencionMedica {
	
 // Atributos de clase AtencionMedica
 private String idAtencion;
 private LocalDateTime fechaAtencion;
 private String diagnostico;
 private String tratamiento;

 private List<DetalleReceta> detallesReceta = new ArrayList<>();

 
 
 //Getters and setters
 public String getIdAtencion() {
	return idAtencion;
}

 public void setIdAtencion(String idAtencion) {
	this.idAtencion = idAtencion;
 }

 public LocalDateTime getFechaAtencion() {
	return fechaAtencion;
 }

 public void setFechaAtencion(LocalDateTime fechaAtencion) {
	this.fechaAtencion = fechaAtencion;
 }

 public String getDiagnostico() {
	return diagnostico;
 }

 public void setDiagnostico(String diagnostico) {
	this.diagnostico = diagnostico;
 }

 public String getTratamiento() {
	return tratamiento;
 }

 public void setTratamiento(String tratamiento) {
	this.tratamiento = tratamiento;
 }

 public List<DetalleReceta> getDetallesReceta() {
	return detallesReceta;
 }

 public void setDetallesReceta(List<DetalleReceta> detallesReceta) {
	this.detallesReceta = detallesReceta;
 }

 public AtencionMedica(String id, LocalDateTime fecha, String diag, String trat) {
     this.idAtencion = id;
     this.fechaAtencion = fecha;
     this.diagnostico = diag;
     this.tratamiento = trat;
 }

 public void agregarDetalleReceta(DetalleReceta detalle) {
     if(detalle.validarStock()) {
         detallesReceta.add(detalle);
         detalle.getMedicamento().actualizarStock(-detalle.getCantidad());
     }
 }

 public String generarInforme() {
     
     String medStr = detallesReceta.stream()
         .map(d -> d.getMedicamento().getNombre() + " (Cant: " + d.getCantidad() + ")")
         .collect(Collectors.joining(", "));
     return "Fecha: " + fechaAtencion + " | Diagnóstico Oculto (Privacidad) | Medicación: " + medStr;
 }
}
