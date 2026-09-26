
from datetime import date

class Medicamento:
    def __init__(self):
        self.id_medicamento = ""
        self.nombre = ""
        self.stock_disponible = 0
        self.fecha_vencimiento = None

    def actualizar_stock(self, cant):
        self.stock_disponible += cant

    def esta_vencido(self):
        return date.today() > self.fecha_vencimiento