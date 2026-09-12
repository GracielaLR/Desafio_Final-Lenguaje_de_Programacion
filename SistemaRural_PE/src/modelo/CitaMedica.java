package modelo;

import java.time.LocalDateTime;

public class CitaMedica {
	
	public enum EstadoCita { PENDIENTE, ATENDIDA, CANCELADA } // Integrado para no crear clases extra

    private String idCita;
    private LocalDateTime fechaHora;
    private EstadoCita estado;
    private String motivoConsulta;

    private AtencionMedica atencionMedica; // 0..1 AtencionMedica
    
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

	public String getIdCita() {
		return idCita;
	}

	public void setIdCita(String idCita) {
		this.idCita = idCita;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getMotivoConsulta() {
		return motivoConsulta;
	}

	public void setMotivoConsulta(String motivoConsulta) {
		this.motivoConsulta = motivoConsulta;
	}

	public AtencionMedica getAtencionMedica() {
		return atencionMedica;
	}

	public void setAtencionMedica(AtencionMedica atencionMedica) {
		this.atencionMedica = atencionMedica;
	}

	public void setEstado(EstadoCita estado) {
		this.estado = estado;
	}

    
    
}
