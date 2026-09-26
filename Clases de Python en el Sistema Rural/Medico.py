# medico.py
from Persona import Persona

class Medico(Persona):
    def __init__(self):
        super().__init__()
        self.cmp = ""
        self.especialidad = ""
        self.citas_asignadas = []

    def atender_cita(self, cita):
        cita.registrar_atencion()

    def emitir_receta(self, atencion, detalle):
        atencion.agregar_detalle_receta(detalle)