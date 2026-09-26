# historial_medico.py
from Cita_Medica import EstadoCita

class HistorialMedico:
    def __init__(self):
        self.citas = []

    def agregar_cita(self, cita):
        self.citas.append(cita)

    def get_citas(self):
        return self.citas

    def consultar_atenciones(self):
        return [cita for cita in self.citas if cita.estado == EstadoCita.ATENDIDA]