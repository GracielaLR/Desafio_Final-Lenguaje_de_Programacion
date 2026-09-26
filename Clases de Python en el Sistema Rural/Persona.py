
class Persona:
    def __init__(self):
        self.id_persona = ""
        self.dni = ""
        self.nombres = ""
        self.apellidos = ""
        self.fecha_nacimiento = None
        self.telefono = ""

    def get_nombre_completo(self):
        return f"{self.nombres} {self.apellidos}"

    def get_dni_enmascarado(self):
        if self.dni and len(self.dni) >= 8:
            return f"****{self.dni[4:]}"
        return "N/A"