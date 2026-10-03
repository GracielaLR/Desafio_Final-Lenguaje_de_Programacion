package modelo;

import java.time.LocalDate;

public abstract class Persona {
	
	private String idPersona;
	private String dni;
	private String nombres;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String telefono;

	public String getNombreCompleto() {
		return nombres + " " + apellidos;
	}
	
	//El DNI se enmascara parcialmente para cumplir con la ley N° 29733
	public String getDniEnmascarado() {
		if (dni != null && dni.length() >= 8) {
			return "****" + dni.substring(4);
		}
		return "N/A";
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
