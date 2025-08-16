package EjercicioIntegrador;

import java.util.Random;
import java.util.Scanner;

public class OrniAzul extends Castor implements MamaPata {
    private double propulsion; // 5 a 10 km/s (según consigna)
    private String apodo;

    public OrniAzul(String apodo, double colaLength, double velocidad, double propulsion) throws InvalidPropulsionException {
        super(colaLength, velocidad);
        this.apodo = (apodo == null) ? "Azul" : apodo.trim();
        setPropulsion(propulsion);
    }

    public double getPropulsion() {
        return propulsion;
    }

    public void setPropulsion(double propulsion) throws InvalidPropulsionException {
        // Validación: 5 a 10 km/s inclusive
        if (Double.isNaN(propulsion) || propulsion < 5.0 || propulsion > 10.0) {
            throw new InvalidPropulsionException("Propulsión inválida. Debe ser un valor entre 5 y 10 km/s.");
        }
        this.propulsion = propulsion;
    }

    @Override
    public void nadar() {
        // Sobreescribe el nadar del padre: toma velocidad del padre + su propulsión
        double total = Math.round((velocidad + propulsion) * 100.0) / 100.0;
        System.out.printf("OrniAzul %s nadando: velocidad padre=%.2f, propulsión propia=%.2f => total=%.2f km/s%n",
                apodo, velocidad, propulsion, total);
    }

    @Override
    public void tocarGuitarra() {
        // toca 3 cuerdas aleatorias de 1 a 6
        Random r = new Random();
        System.out.printf("OrniAzul %s toca guitarra: ", apodo);
        for (int i = 0; i < 3; i++) {
            int cuerda = r.nextInt(6) + 1; // 1..6
            System.out.printf("toca cuerda %d%s", cuerda, (i < 2) ? ", " : ".");
        }
        System.out.println();
    }

    @Override
    public void tocarOrgano() {
        Scanner sc = new Scanner(System.in);
        System.out.printf("OrniAzul %s toca órgano (primera parte): Do-Re-Mi.%n", apodo);
        System.out.println("Presione ENTER para continuar y tocar Fa-Sol-La-Si...");
        sc.nextLine(); // espera que el usuario presione Enter
        System.out.printf("OrniAzul %s continua: Fa-Sol-La-Si.%n", apodo);
        // NOTA: no cierro  Scanner(System.in) para no cerrar System.in globalmente.
    }

    @Override
    public String toString() {
        return String.format("OrniAzul{apodo=%s, cola=%.2f, velocidadPadre=%.2f, propulsión=%.2f}",
                apodo, colaLength, velocidad, propulsion);
    }
}

