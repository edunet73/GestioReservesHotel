/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package av1_aa1_gestioreserveshotel;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
/**
 *
 * @author edtop
 */
public class AV1_AA1_GestioReservesHotel {
    
    // declaració de variables globals
    static final int CEST = 30; // capacitat inicial d'habitacions estàndar
    static final int CSUI = 20; // capacitat inicial de suites
    static final int CDEL = 10; // capacitat inicial d'habitacions deluxe
    static Scanner scan = new Scanner(System.in); // scanner del programa
    static final float IVA = 0.21f; // valor de l'IVA
    static final HashMap<String, Integer> CAPACITAT = new HashMap<>(); // capacitat inicial de l'hotel
    static final HashMap<String, Float> PREU_HAB = new HashMap<>(); // preu de les habitacions
    static final HashMap<String, Float> PREU_SERV = new HashMap<>(); // preu dels serveis addicionals
    static HashMap<String, Integer> disponibilitat = new HashMap<>(); // diponibilitat d'habitacions
    static HashMap<Integer, ArrayList<String>> reserves = new HashMap<>(); // reserves
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        // inicialitzar els hashmaps
        inicialitzarPreus();
        // gestió del menú
        int opcio;
        boolean sortir = false;
        while (!sortir) {
            mostrarMenu();
            opcio = scan.nextInt();
            gestionarOpcio(opcio);
            if (opcio == 6) {
                sortir = true;
            }
            System.out.println();
        }
    }
        
    static void inicialitzarPreus() {
        // capacitat de l'hotel
        CAPACITAT.put("Estàndard", CEST);
        CAPACITAT.put("Suite", CSUI);
        CAPACITAT.put("Deluxe", CDEL);
        // preu de les habitacions
        PREU_HAB.put("Estàndard", 50f);
        PREU_HAB.put("Suite", 100f);
        PREU_HAB.put("Deluxe", 150f);
        // preu dels serveis addicionals
        PREU_SERV.put("Esmorzar", 10f);
        PREU_SERV.put("Gimnàs", 15f);
        PREU_SERV.put("Spa", 20f);
        PREU_SERV.put("Piscina", 25f);
        // diponibilitats inicials
        disponibilitat.put("Estàndard", CAPACITAT.get("Estàndard"));
        disponibilitat.put("Suite", CAPACITAT.get("Suite"));
        disponibilitat.put("Deluxe", CAPACITAT.get("Deluxe"));
    }
    
    static void mostrarMenu() {
        System.out.println("===== Gestió de Reserves d'Hotel =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar diponibilitat");
        System.out.println("4. Consultar dades d'una reserva");
        System.out.println("5. Consultar reserves per tipus");
        System.out.println("6. Sortir");
        System.out.print("Selecciona una opció: ");
    }
    
    static void gestionarOpcio(int opcio) {
        switch (opcio) {
            case 1:
                reservarHabitacio();
                break;
            case 2:
                alliberarHabitacio();
                break;
            case 3:
                consultarDisponibilitat();
                break;
            case 4:
                obtindreReserva();
                break;
            case 5:
                obtindreReservaPerTipus();
                break;
            case 6:
                System.out.println("Sortint del sistema de reserves.");
                break;
            default:
                System.out.println("Error! Opció no vàlida.");
                break;
        }
        //System.out.println();
    }

    static void reservarHabitacio() {
        // seleccionar el tipus d'habitació
        String tipus = seleccionarTipusHabitacioDisponible();
        // seleccionar serveis addicionals
        ArrayList<String> serveis = seleccionarServeis();
        // calcular el preu total
        float preu = calcularPreuTotal(tipus, serveis);
        // generar el codi de reserva
        int codi = generarCodiReserva();
        // crear un ArrayList amb la informació de la reserva
        ArrayList<String> infoReserva = new ArrayList<>();
        infoReserva.add(tipus);
        infoReserva.add(Float.toString(preu));
        for (String servei : serveis) {
            infoReserva.add(servei);
        }
        // introduir la reserva en el HashMap reserves
        reserves.put(codi, infoReserva);
        // actualitzar disponibilitat del tipus d'habitació
        disponibilitat.put(tipus, disponibilitat.get(tipus)-1);
        // mostrar reserva correcta
        System.out.println("Reserva creada correctament.");
        System.out.println("Codi de reserva: " + codi);
        System.out.println("Preu total de la reserva: " + preu + "?");
    }
        
    static String seleccionarTipusHabitacioDisponible() {
        // mostrat disponibilitat
        System.out.println();
        System.out.println("Tipus d'habitació disponibles:");
        System.out.println("- Suite (" + disponibilitat.get("Suite") + " disponibles) - " + PREU_HAB.get("Suite") + "? per nit");
        System.out.println("- Estàndard (" + disponibilitat.get("Estàndard") + " disponibles) - " + PREU_HAB.get("Estàndard") + "? per nit");
        System.out.println("- Deluxe(" + disponibilitat.get("Deluxe") + " disponibles) - " + PREU_HAB.get("Deluxe") + "? per nit");
        // seleccionar el tipus d'habitació
        System.out.print("Introdueix el tipus d'habitació que vols reservar: 1 (Estàndard), 2 (Suite) o 3 (Deluxe): ");
        String tipus = seleccionarTipusHabitacio();
        // retornar només si hi ha habitacio disponible del tipus seleccionat
        if (disponibilitat.get(tipus) != 0) {
            return tipus;
        } else {
            return null;
        }
    }
    
    static String seleccionarTipusHabitacio() {
        int tipus = 0;
        String habitacio = null;
        while (tipus < 1 || tipus > 3) {            
            tipus = scan.nextInt();
            switch (tipus) {
                case 1:
                    habitacio = "Estàndard";
                    break;
                case 2:
                    habitacio = "Suite";
                    break;
                case 3:
                    habitacio = "Deluxe";
                    break;
                default:
                    System.out.println("Error! Tipus no vàlid");
                    System.out.print("Introdueix el tipus d'habitació: ");
                    break;
            }
        }
        return habitacio;
    }
    
    static ArrayList<String> seleccionarServeis() {
        ArrayList<String> serveis = new ArrayList<>();
        System.out.println("Selecciona un servei addicional: ");
        System.out.println("1(Esmorzar - 10?), 2(Gimnàs - 15?), 3(Spa - 20?), 4(Piscina - 25?) o 0(Finalitzar)");
        int opcio;
        boolean ok = false; // opció vàlida
        boolean sortir = false;
        String servei = null;
        while (!sortir) {
            System.out.print("Introdueix el servei: ");
            opcio = scan.nextInt();
            switch (opcio) {
                case 0:
                    sortir = true;
                    ok = true;
                    break;
                case 1: 
                    servei = "Esmorzar"; 
                    ok = true;
                    break;
                case 2:
                    servei = "Gimnàs"; 
                    ok = true;
                    break;
                case 3:
                    servei = "Spa"; 
                    ok = true;
                    break;
                case 4:
                    servei = "Piscina"; 
                    ok = true;
                    break;
                default:
                    System.out.println("Error! Opció no vàlida.");
                    ok = false;
                    break;
            }
            // si la opció és vàlida
            if (ok && !sortir) {
                // afegir el servei
                if (!serveis.contains(servei)) {
                    serveis.add(servei);                        
                } else {
                    System.out.println("Ja has afegit " + servei + ".");
                }
            }
        }
        System.out.println("S'ha finalitzat la introducció de serveis addicionals.");
        return serveis;
    }
    
    static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {
        float preu = 0;
        // afegir el preu de l'habitació
        preu += PREU_HAB.get(tipusHabitacio);
        // si s'han contractat serveis, afegir el preu dels serveis contractats
        if (!serveisSeleccionats.isEmpty()) {
            for (String servei : serveisSeleccionats) {
                preu += PREU_SERV.get(servei);
            }
        }
        // afegir l'IVA
        preu += preu * IVA;
        // retornar el preu total
        return preu;
    }
    
    static int generarCodiReserva() {
        int codi;
        do {
            codi = 100 + (int) (Math.random() * 900);
        } while (reserves.containsKey(codi));
        return codi;        
    }

    static void alliberarHabitacio() {
        int codi;
        String tipus;
        // comprobar que la reserva existeix
        System.out.println();
        do {
            System.out.print("Introdueix el codi de reserva per alliberar l'habitació: ");
            codi = scan.nextInt();
            if (!reserves.containsKey(codi)) {
                System.out.println("Error! El codi de reserva no existeix.");
            }
        } while (!reserves.containsKey(codi));
        // obtindre el tipus d'habitació
        tipus = reserves.get(codi).get(0);
        // actualitzar la disponibilitat del tipus d'habitació corresponent
        disponibilitat.put(tipus, disponibilitat.get(tipus) + 1);
        // eliminar la reserva
        reserves.remove(codi);
        // mostrar missatge
        System.out.println("Reserva alliberada correctament.");
    }
    
    static void consultarDisponibilitat() {
        int ds = disponibilitat.get("Suite");
        int de = disponibilitat.get("Estàndard");
        int dd = disponibilitat.get("Deluxe");
        System.out.println();
        System.out.println("Disponibilitat d'habitacions: ");
        System.out.println("- Suite: " + ds + " lliures, " + (CSUI - ds) + " ocupades");
        System.out.println("- Estàndard: " + de + " lliures, " + (CEST - de) + " ocupades");
        System.out.println("- Deluxe: " + dd + " lliures, " + (CDEL - dd) + " ocupades");
    }
        
    static void obtindreReserva() {
        int codi;
        String tipus;
        // comprobar que la reserva existeix
        System.out.println();
        do {
            System.out.print("Introdueix el codi de reserva: ");
            codi = scan.nextInt();
            if (!reserves.containsKey(codi)) {
                System.out.println("Error! El codi de reserva no existeix.");
            }
        } while (!reserves.containsKey(codi));
        // mostrar la informació de la reserva
        mostrarDadesReserva(codi);        
    }
    
    static void mostrarDadesReserva(int codi) {
        // mostrar la informació de la reserva
        System.out.println("Dades de la reserva:");
        System.out.println("- Codi de reserva: " + codi);
        System.out.println("- Tipus d'habitació: " + reserves.get(codi).get(0));
        System.out.println("- Preu total: " + reserves.get(codi).get(1) + "?");
        System.out.println("- Serveis addicionals:");
        for (int i = 2; i <= reserves.get(codi).size() - 1; i++) {
            System.out.println("   -> " + reserves.get(codi).get(i));
        }
    }

    static void obtindreReservaPerTipus() {
        // obtindre el tipus d'habitació
        System.out.print("Introdueix un tipus d'habitació: 1 (Estàndard), 2 (Suite) o 3 (Deluxe): ");
        String tipus = seleccionarTipusHabitacio();
        // crear un Array amb els codis de les reserves
        int codis[] = new int[reserves.size()];
        int i = 0;
        // per a cada codi en el HashMap reserves, introduir el codi en l'Array codis
        for (int codi : reserves.keySet()) {
            codis[i] = codi;
            i++;
        }
        // llistar reserves per tipus
        // llistarReservesPerTipus(codis, tipus);
    }
    
}


