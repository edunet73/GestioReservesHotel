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
    
    // declaraci� de variables globals
    static final int CEST = 30; // capacitat inicial d'habitacions est�ndar
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
        // gesti� del men�
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
        CAPACITAT.put("Est�ndard", CEST);
        CAPACITAT.put("Suite", CSUI);
        CAPACITAT.put("Deluxe", CDEL);
        // preu de les habitacions
        PREU_HAB.put("Est�ndard", (float) 50);
        PREU_HAB.put("Suite", (float) 100);
        PREU_HAB.put("Deluxe", (float) 150);
        // preu dels serveis addicionals
        PREU_SERV.put("Esmorzar", (float) 10);
        PREU_SERV.put("Gimn�s", (float) 15);
        PREU_SERV.put("Spa", (float) 20);
        PREU_SERV.put("Piscina", (float) 25);
        // diponibilitats inicials
        disponibilitat.put("Est�ndard", CAPACITAT.get("Est�ndard"));
        disponibilitat.put("Suite", CAPACITAT.get("Suite"));
        disponibilitat.put("Deluxe", CAPACITAT.get("Deluxe"));
    }
    
    static void mostrarMenu() {
        System.out.println("===== Gesti� de Reserves d'Hotel =====");
        System.out.println("1. Reservar una habitaci�");
        System.out.println("2. Alliberar una habitaci�");
        System.out.println("3. Consultar diponibilitat");
        System.out.println("4. Consultar dades d'una reserva");
        System.out.println("5. Consultar reserves per tipus");
        System.out.println("6. Sortir");
        System.out.print("Selecciona una opci�: ");
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
                System.out.println("Error! Opci� no v�lida.");
                break;
        }
}

    static void reservarHabitacio() {
        // comprobar que l'hotel t� habitacions disponibles
        if (disponibilitat.get("Est�ndard") == 0 && disponibilitat.get("Suite") == 0 && disponibilitat.get("Deluxe") == 0) {
            System.out.println();
            System.out.println("Actualment l'hotel no t� habitacions disponibles.");
            return;
        }
        // seleccionar el tipus d'habitaci�
        String tipus = seleccionarTipusHabitacioDisponible();
        // seleccionar serveis addicionals
        ArrayList<String> serveis = seleccionarServeis();
        // calcular el preu total
        float preu = calcularPreuTotal(tipus, serveis);
        // generar el codi de reserva
        int codi = generarCodiReserva();
        // crear un ArrayList amb la informaci� de la reserva
        ArrayList<String> infoReserva = new ArrayList<>();
        // afegir el tipus d'habitaci� en la posici� 0 de l'ArrayList infoReserva
        infoReserva.add(tipus);
        // afegir el preu de la reserva en la posici� 1 de infoReserva
        infoReserva.add("" + preu);
        // afegir els serveis (si s'han contractac) en les posicions 2 a 5 de infoReserva
	if (!serveis.isEmpty()) {    
            for (String servei : serveis) {
                infoReserva.add(servei);
            }
	}
        // introduir la reserva en el HashMap reserves
        reserves.put(codi, infoReserva);
        // actualitzar disponibilitat del tipus d'habitaci�
        disponibilitat.put(tipus, disponibilitat.get(tipus)-1);
        // mostrar reserva correcta
        System.out.println("Reserva creada correctament.");
        System.out.println("Codi de reserva: " + codi);
        System.out.println("Preu total de la reserva: " + preu + "?");
    }
        
    static String seleccionarTipusHabitacioDisponible() {
        // mostrat disponibilitat
        System.out.println();
        System.out.println("Tipus d'habitaci� disponibles:");
        System.out.println("- Suite (" + disponibilitat.get("Suite") + " disponibles) - " + PREU_HAB.get("Suite") + "? per nit");
        System.out.println("- Est�ndard (" + disponibilitat.get("Est�ndard") + " disponibles) - " + PREU_HAB.get("Est�ndard") + "? per nit");
        System.out.println("- Deluxe(" + disponibilitat.get("Deluxe") + " disponibles) - " + PREU_HAB.get("Deluxe") + "? per nit");
        // seleccionar el tipus d'habitaci�
        System.out.print("Introdueix el tipus d'habitaci� que vols reservar: 1 (Est�ndard), 2 (Suite) o 3 (Deluxe): ");
        String tipus = seleccionarTipusHabitacio();
        // retornar nom�s si hi ha habitacio disponible del tipus seleccionat
        while (disponibilitat.get(tipus) == 0) {
            System.out.println("Actualment l'hotel no t� habitacions disponibles del tipus seleccionat.");
            System.out.print("Seleccione un altre tipus d'habitaci�: ");
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
                    habitacio = "Est�ndard";
                    break;
                case 2:
                    habitacio = "Suite";
                    break;
                case 3:
                    habitacio = "Deluxe";
                    break;
                default:
                    System.out.println("Error! Tipus no v�lid");
                    System.out.print("Introdueix el tipus d'habitaci�: ");
                    break;
            }
        }
        return habitacio;
    }
    
    static ArrayList<String> seleccionarServeis() {    
	// crear l'ArrayList per guardar els serveis
        ArrayList<String> serveis = new ArrayList<>();
	// oferir els serveis
        System.out.println();
        System.out.println("Selecciona un servei addicional: ");
        System.out.println("1(Esmorzar - 10?), 2(Gimn�s - 15?), 3(Spa - 20?), 4(Piscina - 25?) o 0(Finalitzar)");
	// llegir serveis fins que l'usuari finalitze el proc�s
        int opcio;
        String servei = null;
        do { 
            System.out.print("Introdueix el servei: ");
            opcio = scan.nextInt();
            switch (opcio) {
                case 0:
		    System.out.println("S'ha finalitzat la introducci� de serveis addicionals.");
                    break;
                case 1: 
                    servei = "Esmorzar"; 
                    break;
                case 2:
                    servei = "Gimn�s"; 
                    break;
                case 3:
                    servei = "Spa"; 
                    break;
                case 4:
                    servei = "Piscina"; 
                    break;
                default:
                    System.out.println("Error! Opci� no v�lida.");
                    break;
	    }
	    // si la opcio es v�lida (1-4)
            if (opcio >=1 && opcio <= 4) {
                // introduir el servei en l'ArrayList serveis, comprobant que encara no s'habia introdu�t
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
        // afegir el preu de l'habitaci�
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
                System.out.print("Introdueix el codi de reserva per alliberar l'habitaci�: ");
                codi = scan.nextInt();
                if (!reserves.containsKey(codi)) {
                    System.out.println("Error! El codi de reserva no existeix.");
                }
            } while (!reserves.containsKey(codi));
            // obtindre l'ArrayList amb la informaci� de la reserva
            ArrayList<String> info = reserves.get(codi);
            // obtindre el tipus d'habitaci� (posici� 0 de l'ArrayList)
            tipus = info.get(0);
            // actualitzar la disponibilitat del tipus d'habitaci� corresponent
            disponibilitat.put(tipus, disponibilitat.get(tipus) + 1);
            // eliminar la reserva
            reserves.remove(codi);
            // mostrar missatge
            System.out.println("Reserva alliberada correctament.");
        } else {
            System.out.println();
            System.out.println("Error! Actualment l'hotel no t� reserves.");
        }
    }
    
    static void consultarDisponibilitat() {
        int ds = disponibilitat.get("Suite");
        int de = disponibilitat.get("Est�ndard");
        int dd = disponibilitat.get("Deluxe");
        System.out.println();
        System.out.println("Disponibilitat d'habitacions: ");
        System.out.println("- Suite: " + ds + " lliures, " + (CSUI - ds) + " ocupades");
        System.out.println("- Est�ndard: " + de + " lliures, " + (CEST - de) + " ocupades");
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
        // mostrar la informaci� de la reserva
        mostrarDadesReserva(codi);        
    }
    
    static void mostrarDadesReserva(int codi) {
        // mostrar la informaci� de la reserva
        System.out.println("Dades de la reserva:");
        System.out.println("- Codi de reserva: " + codi);
        System.out.println("- Tipus d'habitaci�: " + reserves.get(codi).get(0));
        System.out.println("- Preu total: " + reserves.get(codi).get(1) + "?");
        System.out.println("- Serveis addicionals:");
        if (reserves.get(codi).size() == 2) {
            System.out.println("   -> No s'han contractat serveis addicionals");
        } else {
            for (int i = 2; i <= reserves.get(codi).size() - 1; i++) {
                System.out.println("   -> " + reserves.get(codi).get(i));
            }
        }
    }

    static void obtindreReservaPerTipus() {
        // si existeixen reserves
        if (!reserves.isEmpty()) {
            // obtindre el tipus d'habitaci�
            System.out.println();
            System.out.print("Introdueix un tipus d'habitaci�: 1 (Est�ndard), 2 (Suite) o 3 (Deluxe): ");
            String tipus = seleccionarTipusHabitacio();
            // crear un array amb els codis de les reserves
            int codis[] = new int[reserves.size()];
            // per a cada codi en el HashMap reserves, introduir-lo en l'array codis
            int i = 0;
            for (int codi : reserves.keySet()) {
                codis[i] = codi;
                i++;
            }
            // llistar reserves per tipus
            llistarReservesPerTipus(codis, tipus);
        } else {
            System.out.println();
            System.out.println("Error! Actualment l'hotel no t� reserves.");
        }
    }
    
    static void llistarReservesPerTipus(int[] codis, String tipus) {
        // si el tipus d'habitaci� de la reserva coincideix amb tipus, mostrar les dades de la reserva
        if (reserves.get(codis[0]).get(0).equals(tipus)) {
            mostrarDadesReserva(codis[0]);
        }
        // si queden codis de reserva en l'array
        if (codis.length > 1) {
            // crear un nou array de tamanay una unitat menor que codis
            int newCodis[] = new int[codis.length-1];
            // copiar codis en newCodis amb la posici� 0 eliminada
            System.arraycopy(codis, 1, newCodis, 0, newCodis.length);
            // cridada recursiva a llistarReservesPerTipus amb newCodis
            llistarReservesPerTipus(newCodis, tipus);
        }
        
    }
    
}


