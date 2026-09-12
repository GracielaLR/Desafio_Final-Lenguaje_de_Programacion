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
    
}
