package Ejercicio1;

import java.util.List;
import java.util.Scanner;

public class MixRepasoej1 {
    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private static final Gestor gestor = new Gestor();

    public static void main(String[] args) {
        System.out.println("=== Aplicación Gestor de Empleados ===");
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opción: ", 0, 6);
            switch (opcion) {
                case 1 -> accionAgregar();
                case 2 -> accionListar();
                case 3 -> accionBuscar();
                case 4 -> accionEliminar();
                case 5 -> accionActualizarSalario();
                case 6 -> mostrarAyuda();
                case 0 -> {
                    System.out.println("Saliendo. ¡Hasta luego!");
                    salir = true;
                }
                default -> System.out.println("Opción no reconocida. Intenta nuevamente.");
            }
            System.out.println();
        }
    }

    private static void mostrarMenu() {
        System.out.println("""
                -------------------------------
                Menú principal:
                1 - Añadir empleado
                2 - Listar empleados
                3 - Buscar empleado por ID
                4 - Eliminar empleado por ID
                5 - Actualizar salario por ID
                6 - Ayuda
                0 - Salir
                -------------------------------
                """);
    }

    private static void accionAgregar() {
        try {
            int id = leerEntero("ID (entero positivo): ", 1, Integer.MAX_VALUE);
            String nombre = leerNombre("Nombre (2-60 letras): ");
            double salario = leerDouble("Salario (>=0): ", 0, Double.MAX_VALUE);

            Empleado emp = new Empleado(id, nombre, salario);
            boolean ok = gestor.agregarEmpleado(emp);
            if (ok) {
                System.out.println("Empleado agregado: " + emp);
            } else {
                System.out.println("No se pudo agregar. Ya existe un empleado con id=" + id);
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void accionListar() {
        List<Empleado> todos = gestor.listarTodos();
        if (todos.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        System.out.println("Lista de empleados:");
        todos.forEach(e -> System.out.println("  - " + e));
    }

    private static void accionBuscar() {
        int id = leerEntero("ID a buscar: ", 1, Integer.MAX_VALUE);
        gestor.buscarPorId(id).ifPresentOrElse(
                e -> System.out.println("Encontrado: " + e),
                () -> System.out.println("No existe empleado con id=" + id)
        );
    }

    private static void accionEliminar() {
        int id = leerEntero("ID a eliminar: ", 1, Integer.MAX_VALUE);
        boolean ok = gestor.eliminarPorId(id);
        System.out.println(ok ? "Empleado eliminado." : "No se encontró empleado con id=" + id);
    }

    private static void accionActualizarSalario() {
        int id = leerEntero("ID a actualizar: ", 1, Integer.MAX_VALUE);
        double nuevo = leerDouble("Nuevo salario (>=0): ", 0, Double.MAX_VALUE);
        try {
            gestor.actualizarSalario(id, nuevo);
            System.out.println("Salario actualizado.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void mostrarAyuda() {
        System.out.println("Ayuda: Use las opciones del menú. Los campos tienen validación básica.");
    }

    /* -------------------- Helpers de entrada con validación -------------------- */

    private static int leerEntero(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = scanner.next().trim();
                int v = Integer.parseInt(line);
                if (v < min || v > max) {
                    System.out.printf("Valor fuera de rango (%d - %d). Intenta otra vez.%n", min, max);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduce un número entero.");
            } catch (Exception ex) {
                System.out.println("Error de entrada: " + ex.getMessage());
            }
        }
    }

    private static double leerDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = scanner.next().trim();
                double v = Double.parseDouble(line);
                if (Double.isNaN(v) || Double.isInfinite(v) || v < min || v > max) {
                    System.out.println("Número inválido o fuera de rango. Intenta otra vez.");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduce un número (ej: 1234.56).");
            } catch (Exception ex) {
                System.out.println("Error de entrada: " + ex.getMessage());
            }
        }
    }

    private static String leerNombre(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String n = scanner.next().trim();
                Empleado.validateNombre(n);
                return n;
            } catch (IllegalArgumentException ex) {
                System.out.println("Nombre inválido: " + ex.getMessage());
            }
        }
    }
}

