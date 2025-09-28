package Ejercicio2;

// Excepción lanzada cuando no se encuentra un estudiante por DNI.

public class EstudianteNoEncontradoException extends Exception {
    public EstudianteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

