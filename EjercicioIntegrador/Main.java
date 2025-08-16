package EjercicioIntegrador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OrniVerde orniVerde = null;
        OrniAzul orniAzul1 = null;
        OrniAzul orniAzul2 = null;
        Castor[] arregloHermanos = new Castor[3]; // arreglo de tamaño 3
        ArrayList<OrniAzul> mejoresNadadores = new ArrayList<>();

        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU (Elija una opción) ---");
            System.out.println("1) Crear 1 OrniVerde y 2 OrniAzul (blue y blui)");
            System.out.println("2) Ejecutar TODAS las habilidades de cada orni creado");
            System.out.println("3) Guardar en arreglo de tamaño 3 (guardar OrniVerde en posicion 1) [manejo de excepciones]");
            System.out.println("4) Pasar a un List los 2 mejores nadadores (solo OrniAzul), ordenar por propulsión ascendente y mostrar con Iterator");
            System.out.println("5) Mostrar estado actual (instancias y arreglo)");
            System.out.println("6) Salir");
            System.out.print("Opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    if (orniVerde != null || orniAzul1 != null || orniAzul2 != null) {
                        System.out.println("Ya existen instancias creadas. Si desea recrearlas, reinicie el programa.");
                        break;
                    }
                    try {
                        System.out.println("Creando OrniVerde:");
                        System.out.print("Nombre para OrniVerde: ");
                        String nombre = sc.nextLine().trim();
                        double colaV = pedirDoublePositivo(sc, "Longitud cola (cm) OrniVerde (ej: 7.5): ");
                        double velV = pedirDoublePositivo(sc, "Velocidad padre para OrniVerde (km/s) (ej: 2.0): ");
                        orniVerde = new OrniVerde(nombre, colaV, velV);

                        System.out.println("\nCreando OrniAzul 'blue':");
                        double colaA1 = pedirDoublePositivo(sc, "Longitud cola (cm) OrniAzul blue: ");
                        double velA1 = pedirDoublePositivo(sc, "Velocidad padre (km/s) para blue: ");
                        double prop1 = pedirPropulsionValida(sc, "Propulsión para blue (5 a 10 km/s): ");
                        orniAzul1 = new OrniAzul("blue", colaA1, velA1, prop1);

                        System.out.println("\nCreando OrniAzul 'blui':");
                        double colaA2 = pedirDoublePositivo(sc, "Longitud cola (cm) OrniAzul blui: ");
                        double velA2 = pedirDoublePositivo(sc, "Velocidad padre (km/s) para blui: ");
                        double prop2 = pedirPropulsionValida(sc, "Propulsión para blui (5 a 10 km/s): ");
                        orniAzul2 = new OrniAzul("blui", colaA2, velA2, prop2);

                        System.out.println("Instancias creadas correctamente.");
                    } catch (InvalidPropulsionException ipe) {
                        System.out.println("Error creando OrniAzul: " + ipe.getMessage());
                    } catch (IllegalArgumentException iae) {
                        System.out.println("Entrada inválida: " + iae.getMessage());
                    }
                    break;

                case "2":
                    // Ejecutar todas las habilidades de todos los ornitorrincos creados
                    if (orniVerde == null || orniAzul1 == null || orniAzul2 == null) {
                        System.out.println("Faltan instancias. Cree primero con la opción 1.");
                        break;
                    }
                    System.out.println("\n--- Ejecutando habilidades ---");

                    // OrniVerde
                    System.out.println("\nOrniVerde:");
                    orniVerde.nadar();               // usa Castor.nadar()
                    orniVerde.tocarGuitarra();       // invoca la versión que delega al padre
                    orniVerde.tocarOrgano();         // implementacion propia
                    orniVerde.tocarGuitorgan();      // habilidad compuesta

                    // OrniAzul 1
                    System.out.println("\nOrniAzul blue:");
                    orniAzul1.nadar();
                    orniAzul1.tocarGuitarra();
                    orniAzul1.tocarOrgano();

                    // OrniAzul 2
                    System.out.println("\nOrniAzul blui:");
                    orniAzul2.nadar();
                    orniAzul2.tocarGuitarra();
                    orniAzul2.tocarOrgano();

                    break;

                case "3":
                    System.out.println("\n--- Guardar en arreglo de tamaño 3 ---");
                    try {
                        // Para poder guardar orniVerde en la posición 1 (índice 1)
                        if (orniVerde == null || orniAzul1 == null || orniAzul2 == null) {
                            System.out.println("Primero cree las instancias (opción 1).");
                            break;
                        }
                        arregloHermanos[0] = orniAzul1;
                        arregloHermanos[1] = orniVerde; // la consigna pedía posición 1 -> lo dejamos en índice 1
                        arregloHermanos[2] = orniAzul2;
                        System.out.println("Arreglo poblado correctamente.");
                        // Forzar un acceso fuera de rango para demostrar manejo? no necesario,
                        //  pero mostraremos acceso protegido.
                        System.out.println("Mostrando arreglo:");
                        for (int i = 0; i < arregloHermanos.length; i++) {
                            System.out.println("Pos " + i + ": " + arregloHermanos[i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException aie) {
                        System.out.println("¡Error! Índice fuera de rango: " + aie.getMessage());
                    } finally {
                        System.out.println("Finally: los ornitohermanos estan juntos al fin...");
                    }
                    break;

                case "4":
                    // Pasar a un List los 2 mejores nadadores, solo "si es instancia de" OrniAzul
                    System.out.println("\n--- Lista de mejores nadadores (solo OrniAzul) ---");
                    mejoresNadadores.clear();
                    // Tomamos orniAzul1 y orniAzul2 si existen
                    if (orniAzul1 != null && orniAzul2 != null) {
                        mejoresNadadores.add(orniAzul1);
                        mejoresNadadores.add(orniAzul2);
                    } else {
                        System.out.println("Faltan OrniAzul creados (use opción 1).");
                        break;
                    }

                    // Ordenamos por propulsión ascendente
                    Collections.sort(mejoresNadadores, Comparator.comparingDouble(OrniAzul::getPropulsion));

                    // Mostrar con Iterator
                    Iterator<OrniAzul> it = mejoresNadadores.iterator();
                    System.out.println("Mejores nadadores ordenados por propulsión ascendente:");
                    while (it.hasNext()) {
                        OrniAzul o = it.next();
                        System.out.println(o);
                    }
                    break;

                case "5":
                    System.out.println("\n--- Estado actual ---");
                    System.out.println("OrniVerde: " + (orniVerde == null ? "NO CREADO" : orniVerde));
                    System.out.println("OrniAzul1: " + (orniAzul1 == null ? "NO CREADO" : orniAzul1));
                    System.out.println("OrniAzul2: " + (orniAzul2 == null ? "NO CREADO" : orniAzul2));
                    System.out.println("Arreglo hermanos:");
                    for (int i = 0; i < arregloHermanos.length; i++) {
                        System.out.printf("  [%d] -> %s%n", i, arregloHermanos[i] == null ? "VACIO" : arregloHermanos[i]);
                    }
                    break;

                case "6":
                    System.out.println("Saliendo...");
                    running = false;
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        // No cerramos sc para dejar System.in usable en otras partes; si cerras debes volver a abrir.
        System.out.println("Programa terminado.");
    }

    // Métodos utilitarios para validar entrada
    private static double pedirDoublePositivo(Scanner sc, String prompt) {
        double val = -1;
        while (val <= 0) {
            System.out.print(prompt);
            String linea = sc.nextLine().trim();
            try {
                val = Double.parseDouble(linea);
                if (val <= 0) {
                    System.out.println("Ingrese un número positivo.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Formato inválido. Intente con un número (ej: 7.5).");
            }
        }
        // redondeo razonable con Math
        return Math.round(val * 100.0) / 100.0;
    }

    private static double pedirPropulsionValida(Scanner sc, String prompt) {
        double p = Double.NaN;
        while (Double.isNaN(p) || p < 5.0 || p > 10.0) {
            System.out.print(prompt);
            String linea = sc.nextLine().trim();
            try {
                p = Double.parseDouble(linea);
                if (p < 5.0 || p > 10.0) {
                    System.out.println("La propulsión debe estar entre 5 y 10 km/s.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Entrada inválida. Ingrese un número entre 5 y 10.");
            }
        }
        return Math.round(p * 100.0) / 100.0;
    }
}

