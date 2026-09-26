
class AtencionMedica:
    def __init__(self, id_atencion, fecha, diag, trat):
        self.id_atencion = id_atencion
        self.fecha_atencion = fecha
        self.diagnostico = diag
        self.tratamiento = trat
        self.detalles_receta = []

    def agregar_detalle_receta(self, detalle):
        if detalle.validar_stock():
            self.detalles_receta.append(detalle)
            detalle.medicamento.actualizar_stock(-detalle.cantidad)

    def generar_informe(self):
        med_str = ", ".join(
            [f"{d.medicamento.nombre} (Cant: {d.cantidad})" for d in self.detalles_receta]
        )
        return f"Fecha: {self.fecha_atencion.strftime('%Y-%m-%d %H:%M')} | Diagnóstico Oculto (Privacidad) | Medicación: {med_str}"