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


def main():
    print("=== Registro de cita médica ===")

    id_persona = input("ID del paciente: ")
    dni = input("DNI: ")
    nombres = input("Nombres: ")
    apellidos = input("Apellidos: ")
    historia_clinica = input("Número de historia clínica: ")
    id_cita = input("ID de la cita: ")

    while True:
        fecha_texto = input("Fecha y hora (AAAA-MM-DD HH:MM): ")
        try:
            fecha_hora = datetime.strptime(fecha_texto, "%Y-%m-%d %H:%M")
            break
        except ValueError:
            print("Formato inválido. Usa AAAA-MM-DD HH:MM.")

    paciente = Paciente(
        id_persona,
        dni,
        nombres,
        apellidos,
        historia_clinica,
    )
    cita = CitaMedica(id_cita, paciente, fecha_hora)

    cancelar = input("¿Deseas cancelar la cita? (s/n): ").strip().lower()
    if cancelar == "s":
        cita.cancelar()

    print("\n=== Datos de la cita ===")
    print(f"Paciente: {cita.paciente.get_nombre_completo()}")
    print(f"DNI: {cita.paciente._dni}")
    print(f"Historia clínica: {cita.paciente.num_historia_clinica}")
    print(f"Fecha y hora: {cita.fecha_hora.strftime('%Y-%m-%d %H:%M')}")
    print(f"Estado: {cita.estado.value}")


if __name__ == "__main__":
    main()