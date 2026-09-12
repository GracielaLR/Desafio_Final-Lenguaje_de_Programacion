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
