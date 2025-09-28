
package EjercicioIntegrador;

public class OrniVerde extends Castor implements MamaPata {
    private String nombre;

    public OrniVerde(String nombre, double colaLength, double velocidad) {
        super(colaLength, velocidad);
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    // OrniVerde no sobreescribe nadar(), usa la implementación del padre Castor.
     
   // OrniVerde implementa tocarGuitarra. En la consigna, el Guitorgan debe invocar
   
  // Al método tocarGuitarra del padre y al tocarOrgano (interface).
    
    @Override
    public void tocarGuitarra() {
        System.out.printf("%s (%s) ejecuta su versión de tocarGuitarra -> delega al padre:%n", "OrniVerde", nombre);
        super.tocarGuitarra(); // invoca la versión del padre
    }

    @Override
    public void tocarOrgano() {
        System.out.printf("OrniVerde %s toca órgano: Do-Re-Mi - Fa-Sol-La-Si.%n", nombre);
    }

    // Nueva habilidad: tocarGuitorgan() que combina guitarra (padre) y órgano.
     
    public void tocarGuitorgan() {
        System.out.printf("OrniVerde %s comienza a tocar el Guitorgan...%n", nombre);
        // invoca el método de la superclase (padre)
        super.tocarGuitarra();
        // invoca el método de la interface implementado en esta clase
        this.tocarOrgano();
        System.out.println("cuac cuaac...!");
    }

    @Override
    public String toString() {
        return String.format("OrniVerde{nombre=%s, cola=%.2f, velocidad=%.2f}", nombre, colaLength, velocidad);
    }
}

