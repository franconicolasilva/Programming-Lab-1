package Ejercicio2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Registro de alumnos con operaciones: agregar, modificar promedio, eliminar y mostrar.
 
public class RegistroAlumnos {

    private final HashMap<Integer, Estudiante> listado;
    private final Scanner scanner;

    public RegistroAlumnos() {
        this.listado = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    /* ------------------ MÉTODOS DE ENTRADA / VALIDACIÓN ------------------ */

    private int leerEnteroPositivo(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor <= 0) {
                    System.out.println("Debe ingresar un número entero positivo. Intente de nuevo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Por favor ingrese un número entero.");
            }
        }
    }

    private double leerPromedioValido(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double valor = Double.parseDouble(scanner.nextLine().trim());
                // Validación de rango (0 a 10)
                if (valor < 0.0 || valor > 10.0) {
                    System.out.println("Promedio inválido. Debe ser un número entre 0.0 y 10.0.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Por favor ingrese un número (por ejemplo 7.5).");
            }
        }
    }

    private String leerTextoNoVacio(String prompt) {
        while (true) {
            System.out.print(prompt);
            String texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El texto no puede estar vacío. Intente de nuevo.");
                continue;
            }
            return texto;
        }
    }

    private int leerOpcionMenu() {
        while (true) {
            try {
                System.out.print("Seleccione una opción: ");
                int op = Integer.parseInt(scanner.nextLine().trim());
                return op;
            } catch (NumberFormatException ex) {
                System.out.println("Opción inválida. Ingrese el número de la opción.");
            }
        }
    }

    /* ------------------ OPERACIONES SOBRE EL REGISTRO ------------------ */

    // Para agregar un estudiante nuevo (clave: DNI, valor: Estudiante).
    // Si el DNI ya existe, pregunta si desea sobrescribir.
    
    public void agregarEstudiante() {
        System.out.println("\n=== AGREGAR ESTUDIANTE ===");
        int dni = leerEnteroPositivo("Ingrese DNI: ");
        if (listado.containsKey(dni)) {
            System.out.println("Ya existe un estudiante con DNI " + dni + ": " + listado.get(dni));
            String respuesta;
            while (true) {
                System.out.print("¿Desea sobrescribirlo? (si/no): ");
                respuesta = scanner.nextLine().trim();
                if (respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("no")) break;
                System.out.println("Respuesta inválida. Ingrese 'si' o 'no'.");
            }
            if (respuesta.equalsIgnoreCase("no")) {
                System.out.println("No se agregó el estudiante.");
                return;
            }
            // si responde "si", se continúa para sobrescribir
        }

        String nombre = leerTextoNoVacio("Ingrese nombre: ");
        String apellido = leerTextoNoVacio("Ingrese apellido: ");
        double promedio = leerPromedioValido("Ingrese promedio (0.0 - 10.0): ");

        Estudiante nuevo = new Estudiante(nombre, apellido, promedio);
        listado.put(dni, nuevo);
        System.out.println("Estudiante agregado/actualizado correctamente.");
    }

    // Modifica el promedio de un estudiante existente.
    // Lanza EstudianteNoEncontradoException si no existe.
     
    public void modificarPromedio() {
        System.out.println("\n=== MODIFICAR PROMEDIO ===");
        int dni = leerEnteroPositivo("Ingrese DNI del alumno a modificar: ");
        try {
            if (!listado.containsKey(dni)) {
                throw new EstudianteNoEncontradoException("No existe alumno con DNI " + dni);
            }
            double nuevoPromedio = leerPromedioValido("Ingrese nuevo promedio (0.0 - 10.0): ");
            listado.get(dni).setPromedio(nuevoPromedio);
            System.out.println("Promedio actualizado correctamente.");
        } catch (EstudianteNoEncontradoException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    // Elimina un estudiante según su DNI.
    // Lanza EstudianteNoEncontradoException si no existe.
    
    public void eliminarEstudiante() {
        System.out.println("\n=== ELIMINAR ESTUDIANTE ===");
        int dni = leerEnteroPositivo("Ingrese DNI del alumno a eliminar: ");
        try {
            if (!listado.containsKey(dni)) {
                throw new EstudianteNoEncontradoException("No existe alumno con DNI " + dni);
            }
            // Confirmación
            System.out.print("¿Confirma eliminación del alumno " + listado.get(dni) + " ? (si/no): ");
            String confirmar = scanner.nextLine().trim();
            if (confirmar.equalsIgnoreCase("si")) {
                listado.remove(dni);
                System.out.println("Estudiante eliminado correctamente.");
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (EstudianteNoEncontradoException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    // Para Mostrar todos los estudiantes recorriendo el HashMap con for-each.
     
    public void mostrarEstudiantes() {
        System.out.println("\n=== LISTADO DE ESTUDIANTES ===");
        if (listado.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        for (Map.Entry<Integer, Estudiante> entrada : listado.entrySet()) {
            Integer clave = entrada.getKey();
            Estudiante valor = entrada.getValue();
            System.out.println("D.N.I: " + clave + " -> " + valor);
        }
    }

    // Añado un Menu para que se vea mas formal.
     
    public void ejecutarMenu() {
        System.out.println("======== REGISTRO DE ALUMNOS ========");
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú:");
            System.out.println("1. Agregar estudiante");
            System.out.println("2. Modificar promedio");
            System.out.println("3. Eliminar estudiante");
            System.out.println("4. Mostrar todos los estudiantes");
            System.out.println("5. Salir");
            int opcion = leerOpcionMenu();
            switch (opcion) {
                case 1 -> agregarEstudiante();
                case 2 -> modificarPromedio();
                case 3 -> eliminarEstudiante();
                case 4 -> mostrarEstudiantes();
                case 5 -> {
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    salir = true;
                }
                default -> System.out.println("Opción no válida. Ingrese un número entre 1 y 5.");
            }
        }
    }
}

