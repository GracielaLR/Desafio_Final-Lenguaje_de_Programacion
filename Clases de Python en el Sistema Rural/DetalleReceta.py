
class DetalleReceta:
    def __init__(self):
        self.cantidad = 0
        self.indicaciones = ""
        self.medicamento = None

    def validar_stock(self):
        return (self.medicamento is not None and 
                self.medicamento.stock_disponible >= self.cantidad and 
                not self.medicamento.esta_vencido())