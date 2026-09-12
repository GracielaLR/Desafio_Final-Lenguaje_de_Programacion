package modelo;

import java.time.LocalDate;

public class Medicamento {

	private String idMedicamento;
    private String nombre;
    private int stockDisponible;
    private LocalDate fechaVencimiento;
	
    public void actualizarStock(int cant) {
    	this.stockDisponible += cant;
    }
    
    public boolean estaVencido() {
    	return LocalDate.now().isAfter(fechaVencimiento);
    	
    }
    
    public String getNombre() {return nombre;}
    
    public int getStockDisponible() {return stockDisponible;}

	public String getIdMedicamento() {
		return idMedicamento;
	}

	public void setIdMedicamento(String idMedicamento) {
		this.idMedicamento = idMedicamento;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setStockDisponible(int stockDisponible) {
		this.stockDisponible = stockDisponible;
	}
    
    
    
}
