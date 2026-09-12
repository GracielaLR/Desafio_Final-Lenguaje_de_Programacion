from datetime import datetime
from enum import Enum

class EstadoCita(Enum):
    PROGRAMADA = "Programada"
    ATENDIDA = "Atendida"
    CANCELADA = "Cancelada"

class Persona:
    def __init__(self, id_persona: str, dni: str, nombres: str, apellidos: str):
        self._id_persona = id_persona
        self._dni = dni
        self._nombres = nombres
        self._apellidos = apellidos

    def get_nombre_completo(self) -> str:
        return f"{self._nombres} {self._apellidos}"

class Paciente(Persona):
    def __init__(self, id_persona: str, dni: str, nombres: str, apellidos: str, num_historia_clinica: str):
        super().__init__(id_persona, dni, nombres, apellidos)
        self.num_historia_clinica = num_historia_clinica

class CitaMedica:
    def __init__(self, id_cita: str, paciente: Paciente, fecha_hora: datetime):
        self.id_cita = id_cita
        self.paciente = paciente
        self.fecha_hora = fecha_hora
        self.estado = EstadoCita.PROGRAMADA

    def cancelar(self):
        self.estado = EstadoCita.CANCELADA