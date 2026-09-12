package modelo;

//2. Paciente.java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Paciente extends Persona {
 private String numeroHistoriaClinica;
 private String tipoSeguro;
 private List<CitaMedica> citasMedicas = new ArrayList<>(); // 1..* CitaMedica


}


