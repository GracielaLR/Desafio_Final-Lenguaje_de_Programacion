package modelo;

import java.time.LocalDateTime;

public class CitaMedica {
	
	public enum EstadoCita { PENDIENTE, ATENDIDA, CANCELADA }

    private String idCita;
    private LocalDateTime fechaHora;
    private EstadoCita estado;
    private String motivoConsulta;
    
    // NUEVOS ATRIBUTOS DE TRIAJE
    private String especialidad;
    private Medico medicoAsignado;

    private AtencionMedica atencionMedica; 
    
    public void programarCita() {
    	this.estado = EstadoCita.PENDIENTE;
    }
    
    public void cancelarCita() {
    	this.estado = EstadoCita.CANCELADA;
    }
    
    public AtencionMedica registrarAtencion() {
        this.estado = EstadoCita.ATENDIDA;
        this.atencionMedica = new AtencionMedica(this.idCita + "-A", LocalDateTime.now(), "Pendiente de evaluación", "Sin tratamiento inicial");
        return this.atencionMedica;
    }
    
    public EstadoCita getEstado() {return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

	public String getIdCita() { return idCita; }
	public void setIdCita(String idCita) { this.idCita = idCita; }

	public LocalDateTime getFechaHora() { return fechaHora; }
	public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

	public String getMotivoConsulta() { return motivoConsulta; }
	public void setMotivoConsulta(String motivoConsulta) { this.motivoConsulta = motivoConsulta; }

	public AtencionMedica getAtencionMedica() { return atencionMedica; }
	public void setAtencionMedica(AtencionMedica atencionMedica) { this.atencionMedica = atencionMedica; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public Medico getMedicoAsignado() { return medicoAsignado; }
    public void setMedicoAsignado(Medico medicoAsignado) { this.medicoAsignado = medicoAsignado; }
}