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
    static final int CAP_ESTANDARD = 30; // capacitat inicial d'habitacions estàndar
    static final int CAP_SUITE = 20; // capacitat inicial de suites
    static final int CAP_DELUXE = 10; // capacitat inicial d'habitacions deluxe
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
        // inicialitzar els HashMaps
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
        CAPACITAT.put("Estàndard", CAP_ESTANDARD);
        CAPACITAT.put("Suite", CAP_SUITE);
        CAPACITAT.put("Deluxe", CAP_DELUXE);
        // preu de les habitacions
        PREU_HAB.put("Estàndard", (float) 50);
        PREU_HAB.put("Suite", (float) 100);
        PREU_HAB.put("Deluxe", (float) 150);
        // preu dels serveis addicionals
        PREU_SERV.put("Esmorzar", (float) 10);
        PREU_SERV.put("Gimnàs", (float) 15);
        PREU_SERV.put("Spa", (float) 20);
        PREU_SERV.put("Piscina", (float) 25);
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
    }

    static void reservarHabitacio() {
        // comprobar que l'hotel té habitacions disponibles
        if (disponibilitat.get("Estàndard") == 0 && disponibilitat.get("Suite") == 0 && disponibilitat.get("Deluxe") == 0) {
            System.out.println();
            System.out.println("Actualment l'hotel no té habitacions disponibles.");
            return;
        }
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
        // afegir el tipus d'habitació en la posició 0 de l'ArrayList infoReserva
        infoReserva.add(tipus);
        // afegir el preu de la reserva en la posició 1 de infoReserva
        infoReserva.add("" + preu);
        // afegir els serveis (si s'han contractac) en les posicions 2 a 5 de infoReserva
	if (!serveis.isEmpty()) {    
            for (String servei : serveis) {
                infoReserva.add(servei);
            }
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
        while (disponibilitat.get(tipus) == 0) {
            System.out.println("Actualment l'hotel no té habitacions disponibles del tipus seleccionat.");
            System.out.print("Selecciona un altre tipus d'habitació: ");
            tipus = seleccionarTipusHabitacio();
        } 
        return tipus;
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
	// crear l'ArrayList per a guardar els serveis
        ArrayList<String> serveis = new ArrayList<>();
	// oferir els serveis
        System.out.println();
        System.out.println("Selecciona un servei addicional: ");
        System.out.println("1(Esmorzar - 10?), 2(Gimnàs - 15?), 3(Spa - 20?), 4(Piscina - 25?) o 0(Finalitzar)");
	// llegir serveis fins que l'usuari finalitze el procés
        int opcio;
        String servei = null;
        do { 
            System.out.print("Introdueix el servei: ");
            opcio = scan.nextInt();
            switch (opcio) {
                case 0:
		    System.out.println("S'ha finalitzat la introducció de serveis addicionals.");
                    break;
                case 1: 
                    servei = "Esmorzar"; 
                    break;
                case 2:
                    servei = "Gimnàs"; 
                    break;
                case 3:
                    servei = "Spa"; 
                    break;
                case 4:
                    servei = "Piscina"; 
                    break;
                default:
                    System.out.println("Error! Opció no vàlida.");
                    break;
	    }
	    // si la opcio es vàlida (1-4)
            if (opcio >=1 && opcio <= 4) {
                // introduir el servei en l'ArrayList serveis, comprobant que encara no s'habia introduït
                if (!serveis.contains(servei)) {
                    serveis.add(servei);                        
                } else {
                    System.out.println("Ja has afegit " + servei + ".");
                }
            }
        } while (opcio != 0);
	// retornar l'ArrayList amb els serveis
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
	// generar un codi aleatori entre 100 i 999, comprobant que el codi no existeix
        do {
            codi = 100 + (int) (Math.random() * 900);
        } while (reserves.containsKey(codi));
	// retornar el codi
        return codi;        
    }

    static void alliberarHabitacio() {
        // si existeixen reserves
        if (!reserves.isEmpty()) {
            int codi;
            String tipus;
            // demanar el codi de reserva, comprobant que la reserva existeix
            System.out.println();
            do {
                System.out.print("Introdueix el codi de reserva per alliberar l'habitació: ");
                codi = scan.nextInt();
                if (!reserves.containsKey(codi)) {
                    System.out.println("Error! El codi de reserva no existeix.");
                }
            } while (!reserves.containsKey(codi));
            // obtindre l'ArrayList amb la informació de la reserva
            ArrayList<String> info = reserves.get(codi);
            // obtindre el tipus d'habitació (posició 0 de l'ArrayList)
            tipus = info.get(0);
            // actualitzar la disponibilitat del tipus d'habitació corresponent
            disponibilitat.put(tipus, disponibilitat.get(tipus) + 1);
            // eliminar la reserva
            reserves.remove(codi);
            // mostrar missatge
            System.out.println("Reserva alliberada correctament.");
        } else {
            // si no existeixen reserves, informar de la situació
            System.out.println();
            System.out.println("Error! Actualment l'hotel no té reserves.");
        }
    }
    
    static void consultarDisponibilitat() {
	// obtindre la disponibitat de cada tipus d'habitació (HashMap disponibilitat)
        int dispSuite = disponibilitat.get("Suite");
        int dispEst = disponibilitat.get("Estàndard");
        int dispDeluxe = disponibilitat.get("Deluxe");
	// mostrar la informació
        System.out.println();
        System.out.println("Disponibilitat d'habitacions: ");
        System.out.println("- Suite: " + dispSuite + " lliures, " + (CAP_SUITE - dispSuite) + " ocupades");
        System.out.println("- Estàndard: " + dispEst + " lliures, " + (CAP_ESTANDARD - dispEst) + " ocupades");
        System.out.println("- Deluxe: " + dispDeluxe + " lliures, " + (CAP_DELUXE - dispDeluxe) + " ocupades");
    }
        
    static void obtindreReserva() {
        // si existeixen reserves
        if (!reserves.isEmpty()) {
            int codi;
            // demanar el codi de reserva, comprobant que la reserva existeix
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
        } else {
            // si no existeixen reserves, informar de la situació
            System.out.println();
            System.out.println("Error! Actualment l'hotel no té reserves.");
        }
    }
    
    static void mostrarDadesReserva(int codi) {
        // obtindre l'ArrayList amb la informació de la reserva
        ArrayList<String> infoReserva = reserves.get(codi);
        // obtindre el tipus d'habitació (posició 0 de l'ArrayList)
        String tipus = infoReserva.get(0);
        // obtindre el preu de la reserva (posició 1 de l'ArrayList)
        String preu = infoReserva.get(1);
        // mostrar la informació de la reserva
        System.out.println("Dades de la reserva:");
        System.out.println("- Codi de reserva: " + codi);
        System.out.println("- Tipus d'habitació: " + tipus);
        System.out.println("- Preu total: " + preu + "?");
        System.out.println("- Serveis addicionals:");
        // si l'ArrayList infoReserva només conté 2 elements significa que no s'han contractat serveis
        if (infoReserva.size() == 2) {
            System.out.println("   -> No s'han contractat serveis addicionals");
        } else {
            // si conté mes de 2 elements, iterar a partir de la posició 2 fins l'última per mostrar els serveis
            for (int i = 2; i <= infoReserva.size()-1; i++) {
                System.out.println("   -> " + infoReserva.get(i));
            }
        }
    }

    static void obtindreReservaPerTipus() {
        // si existeixen reserves
        if (!reserves.isEmpty()) {
            // obtindre el tipus d'habitació
            System.out.println();
            System.out.print("Introdueix un tipus d'habitació: 1 (Estàndard), 2 (Suite) o 3 (Deluxe): ");
            String tipus = seleccionarTipusHabitacio();
            // si la disponibilitat del tipus seleccionat és menor que la capacitat, tenim reserves d'eixe tipus
            if (disponibilitat.get(tipus) < CAPACITAT.get(tipus)) {
                // crear un array amb els codis de les reserves
                int codis[] = new int[reserves.size()];
                // iterar l'array de codis
                int i = 0; // iterador de l'array
                // per cada codi en el HashMap reserves, introduir-lo en l'array codis i incrementar l'iterador
                for (int codi : reserves.keySet()) {
                    codis[i] = codi;
                    i++;
                }
                // llistar reserves per tipus
                llistarReservesPerTipus(codis, tipus);
            } else {
                // si l'hotel no té reserves del tipus seleccionat, informar de la situació
                System.out.println();
                System.out.println("Error! Actualment l'hotel no té reserves del tipus d'habitació " + tipus + ".");
            }
        } else {
            // si l'hotel no té reserves, informar de la situació
            System.out.println();
            System.out.println("Error! Actualment l'hotel no té reserves.");
        }
    }
    
    static void llistarReservesPerTipus(int[] codis, String tipus) {
        // obtindre el primer codi de reserva de l'array de codis
        int codiReserva = codis[0];
        // obtindre l'ArrayList amb la informació de la reserva
        ArrayList<String> infoReserva = reserves.get(codiReserva);
        // obtindre el tipus d'habitació de la reserva (posició 0 de l'ArrayList)
        String tipusReserva = infoReserva.get(0);
        // si el tipus d'habitació de la reserva coincideix amb tipus, mostrar les dades de la reserva
        if (tipusReserva.equals(tipus)) {
            mostrarDadesReserva(codiReserva);
        }
        // si queden mes codis de reserva en l'array (comprovació que evita que el mètode es cride a si mateix indefinidament)
        if (codis.length > 1) {
            // crear un nou array de tamany una unitat menor que codis
            int newCodis[] = new int[codis.length-1];
            // copiar codis en newCodis amb la posició 0 eliminada
            System.arraycopy(codis, 1, newCodis, 0, newCodis.length);
            // cridada recursiva a llistarReservesPerTipus amb newCodis
            llistarReservesPerTipus(newCodis, tipus);
        }
        
    }
    
}


