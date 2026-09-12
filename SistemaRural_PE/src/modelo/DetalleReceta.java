package modelo;

//Clase que representa el detalle individual de un medicamento recetado.
//Actúa como parte de la receta vinculando una cantidad y dosis con un producto.
public class DetalleReceta {

	private int cantidad;
    private String indicaciones;
    private Medicamento medicamento; // Relación 1 Medicamento

    //Verifica si el medicamento recetado cumple con las condiciones para ser despachado.
    //Evalúa tres reglas de negocio esenciales del sistema de salud.
    public boolean validarStock() {
        return medicamento != null && medicamento.getStockDisponible() >= cantidad && !medicamento.estaVencido();
    }

    //La cantidad recetada del medicamento.
    public Medicamento getMedicamento() { return medicamento; }
    public int getCantidad() { return cantidad; }

	public String getIndicaciones() {
		return indicaciones;
	}

	public void setIndicaciones(String indicaciones) {
		this.indicaciones = indicaciones;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
    
    
}
