# cita_medica.py
from enum import Enum
from datetime import datetime
from Atencion_Medica import AtencionMedica

class EstadoCita(Enum):
    PENDIENTE = "PENDIENTE"
    ATENDIDA = "ATENDIDA"
    CANCELADA = "CANCELADA"

class CitaMedica:
    def __init__(self):
        self.id_cita = ""
        self.fecha_hora = None
        self.estado = None
        self.motivo_consulta = ""
        self.atencion_medica = None

    def programar_cita(self):
        self.estado = EstadoCita.PENDIENTE

    def cancelar_cita(self):
        self.estado = EstadoCita.CANCELADA

    def registrar_atencion(self):
        self.estado = EstadoCita.ATENDIDA
        # Genera y retorna la atención médica
        self.atencion_medica = AtencionMedica(
            f"{self.id_cita}-A", 
            datetime.now(), 
            "Pendiente de evaluación", 
            "Sin tratamiento inicial"
        )
        return self.atencion_medica