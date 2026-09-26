# paciente.py
from Persona import Persona
from Historial_Medico import HistorialMedico

class Paciente(Persona):
    def __init__(self):
        super().__init__()
        self.numero_historia_clinica = ""
        self.tipo_seguro = ""
        self.historial_medico = HistorialMedico()

    def get_citas_medicas(self):
        return self.historial_medico.get_citas()

    def solicitar_cita(self, cita):
        self.historial_medico.agregar_cita(cita)

    def consultar_historial(self):
        return self.historial_medico.consultar_atenciones()