package EjercicioIntegrador;


public class Castor {
    protected double colaLength; // en cm
    protected double velocidad;  // km/s

    public Castor(double colaLength, double velocidad) {
        if (colaLength <= 0) throw new IllegalArgumentException("La cola debe ser positiva.");
        if (velocidad <= 0) throw new IllegalArgumentException("La velocidad debe ser positiva.");
        this.colaLength = colaLength;
        this.velocidad = velocidad;
    }

    public double getColaLength() {
        return colaLength;
    }

    public double getVelocidad() {
        return velocidad;
    }

    // Método base para nadar — puede ser sobreescrito por subclases.
     
    public void nadar() {
        System.out.printf("%sNadando (Castor) a velocidad %.2f km/s.%n", "", velocidad);
    }

    // Método base para tocar guitarra — implementado en el padre.
     
    public void tocarGuitarra() {
        // Versión "padre" determinista
        System.out.println("Castor toca guitarra: toca cuerda 1, toca cuerda 2, toca cuerda 3.");
    }

    @Override
    public String toString() {
        return String.format("Castor{cola=%.2f cm, velocidad=%.2f km/s}", colaLength, velocidad);
    }
}

