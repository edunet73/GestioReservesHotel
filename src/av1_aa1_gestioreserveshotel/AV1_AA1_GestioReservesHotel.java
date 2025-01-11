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
 * @author Eduardo Tolosa Palaz�n
 */
public class AV1_AA1_GestioReservesHotel {
    
    // declaraci� de variables globals
    static final int CAP_ESTANDARD = 30; // capacitat inicial d'habitacions est�ndar
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
     * Controla en un bucle l'aparici� del men�, la gesti� de
     * l'opci� triada per l'usuari i la finalitzaci� del programa
     */
        public static void main(String[] args) {        
        // inicialitzar els HashMaps
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
            System.out.println(); // l�nea de separaci� amb l'eixida anterior
        }
    }
    
    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * disponibilitats inicials
     */
    static void inicialitzarPreus() {
        // capacitat de l'hotel
        CAPACITAT.put("Est�ndard", CAP_ESTANDARD);
        CAPACITAT.put("Suite", CAP_SUITE);
        CAPACITAT.put("Deluxe", CAP_DELUXE);
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
    
    /**
     * Mostra el men� principal amb les opcions disponibles per a l'usuari
     */
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
    
    /**
     * Processa l'opci� seleccionada per l'usuari i crida el m�tode
     * corresponent
     */
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
                System.out.println(); // l�nea de separaci� amb l'eixida anterior
                System.out.println("Sortint del sistema de reserves.");
                break;
            default:
                System.out.println("Error! Opci� no v�lida.");
                break;
        }
    }
    
    /**
     * Gestiona tot el proc�s de reserva, incloent la selecci� del tipus
     * d'habitaci�, serveis addicionals, c�lcul del preu total, i generaci� 
     * d'un codi de reserva �nic
     */
    static void reservarHabitacio() {
        // si l'hotel no t� habitacions disponibles, informar de la situaci� i tornar al men�
        if (disponibilitat.get("Est�ndard") == 0 && disponibilitat.get("Suite") == 0 && disponibilitat.get("Deluxe") == 0) {
            System.out.println(); // l�nea de separaci�
            System.out.println("Actualment l'hotel no t� habitacions disponibles.");
            return;
        }
        // seleccionar el tipus d'habitaci�
        String tipus = seleccionarTipusHabitacioDisponible();
        // si no queden habitacions disponibles del tipus seleccionat, informar de la situaci� i tornar al men�
        if (tipus == null) {
            System.out.println(); // l�nea de separaci�
            System.out.println("Actualment l'hotel no t� habitacions disponibles del tipus seleccionat.");
            return;
        } 
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
    
    /**
     * Mostra la disponibilitat dels tipus d'habitacions i crida a seleccionarTipusHabitacio() 
     * Si del tipus d'habitaci� seleccionat queden habitacions disponibles, retorna el tipus d'habitaci�
     * En cas contrari, retorna null
     */
    static String seleccionarTipusHabitacioDisponible() {
        // mostrat disponibilitat
        System.out.println(); // l�nea de separaci�
        System.out.println("Tipus d'habitaci� disponibles:");
        System.out.println("- Suite (" + disponibilitat.get("Suite") + " disponibles) - " + PREU_HAB.get("Suite") + "? per nit");
        System.out.println("- Est�ndard (" + disponibilitat.get("Est�ndard") + " disponibles) - " + PREU_HAB.get("Est�ndard") + "? per nit");
        System.out.println("- Deluxe(" + disponibilitat.get("Deluxe") + " disponibles) - " + PREU_HAB.get("Deluxe") + "? per nit");
        // seleccionar el tipus d'habitaci�
        System.out.print("Introdueix el tipus d'habitaci� que vols reservar: 1 (Est�ndard), 2 (Suite) o 3 (Deluxe): ");
        String tipus = seleccionarTipusHabitacio();
        // si queden habitacions del tipus seleccionat, retorna el tipus
        if (disponibilitat.get(tipus) != 0) {
            return tipus;
        } else {
            // si no, retorna null
            return null;
        } 
    }
    
    /**
     * Pregunta a l'usuari un tipus d'habitaci� en format num�ric 
     * i retorna un String que cont� el tipus d'habitaci� seleccionat
     */
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
    
    /**
     * Permet a l'usuari triar serveis addicionals que vol afegir a la reserva
     * Retorna un ArrayList de String que tindr� entre 0 i 4 elements (serveis)
     */
    static ArrayList<String> seleccionarServeis() {    
	// crear l'ArrayList per a guardar els serveis
        ArrayList<String> serveis = new ArrayList<>();
	// oferir els serveis
        System.out.println(); // l�nea de separaci�
        System.out.println("Selecciona un servei addicional: ");
        System.out.println("1(Esmorzar - 10?), 2(Gimn�s - 15?), 3(Spa - 20?), 4(Piscina - 25?) o 0(Finalitzar)");
	// llegir serveis fins que l'usuari finalitze el proc�s (opci� 0)
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
    
    /**
     * Calcula i retorna el cost total de la reserva incloent l'habitaci�, 
     * els serveis i l'IVA
     */
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
    
    /**
     * Genera i retorna un codi de reserva �nic de tres xifres que no
     * estiga repetit
     */
    static int generarCodiReserva() {
        int codi;
	// generar un codi aleatori entre 100 i 999, comprobant que el codi no existeix
        // - amb Math.random() * 900 tinc un n�mero entre 0 i 899
        // - sumant-li 100 tinc un n�mero entre 100 i 999
        do {
            codi = 100 + (int) (Math.random() * 900);
        } while (reserves.containsKey(codi));
	// retornar el codi
        return codi;        
    }
    
    /**
     * Allibera una habitaci� utilitzant el codi de reserva i actualitza
     * la disponibilitat del tipus d'habitaci� corresponent
     */
    static void alliberarHabitacio() {
        // si no existeixen reserves, informar de la situaci� i tornar al men�
        if (reserves.isEmpty()) {
            System.out.println();
            System.out.println("Actualment l'hotel no t� reserves.");
            return;
        }
        int codi;
        String tipus;
        // demanar el codi de reserva
        System.out.println();
        System.out.print("Introdueix el codi de reserva per a alliberar l'habitaci�: ");
        codi = scan.nextInt();
        // si el codi de reserva no existeix, informar de la situaci� i tornar al men�
        if (!reserves.containsKey(codi)) {
            System.out.println("Error! El codi de reserva no existeix.");
            return;
        }
        // obtindre l'ArrayList amb la informaci� de la reserva
        ArrayList<String> info = reserves.get(codi);
        // obtindre el tipus d'habitaci� (posici� 0 de l'ArrayList) 
        tipus = info.get(0); // --> alternativa: tipus = reserves.get(codi).get(0); 
        // actualitzar la disponibilitat del tipus d'habitaci� corresponent
        disponibilitat.put(tipus, disponibilitat.get(tipus) + 1);
        // eliminar la reserva
        reserves.remove(codi);
        // mostrar missatge
        System.out.println("Reserva alliberada correctament.");
    }
    
    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades)
     */
    static void consultarDisponibilitat() {
	// obtindre la disponibitat de cada tipus d'habitaci� (HashMap disponibilitat)
        int dispSuite = disponibilitat.get("Suite");
        int dispEst = disponibilitat.get("Est�ndard");
        int dispDeluxe = disponibilitat.get("Deluxe");
	// mostrar la informaci�
        System.out.println();
        System.out.println("Disponibilitat d'habitacions: ");
        System.out.println("- Suite: " + dispSuite + " lliures, " + (CAP_SUITE - dispSuite) + " ocupades");
        System.out.println("- Est�ndard: " + dispEst + " lliures, " + (CAP_ESTANDARD - dispEst) + " ocupades");
        System.out.println("- Deluxe: " + dispDeluxe + " lliures, " + (CAP_DELUXE - dispDeluxe) + " ocupades");
    }
    
    /**
     * Permet consultar els detalls d'una reserva espec�fica introduint el
     * codi de reserva
     */
    static void obtindreReserva() {
        // si no existeixen reserves, informar de la situaci� i tornar al men�
        if (reserves.isEmpty()) {
            System.out.println();
            System.out.println("Actualment l'hotel no t� reserves.");
            return;
        }
        int codi;
        // demanar el codi de reserva
        System.out.println(); // l�nea de separaci�
        System.out.print("Introdueix el codi de reserva: ");
        codi = scan.nextInt();
        // si el codi de reserva no existeix, informar de la situaci� i tornar al men�
        if (!reserves.containsKey(codi)) {
            System.out.println("Error! El codi de reserva no existeix.");
            return;
        }
        // mostrar la informaci� de la reserva
        mostrarDadesReserva(codi);
    }
    
    /**
     * Consulta i mostra en detall la informaci� d'una reserva
     */
    static void mostrarDadesReserva(int codi) {
        // obtindre l'ArrayList amb la informaci� de la reserva
        ArrayList<String> infoReserva = reserves.get(codi);
        // obtindre el tipus d'habitaci� (posici� 0 de l'ArrayList)
        String tipus = infoReserva.get(0);
        // obtindre el preu de la reserva (posici� 1 de l'ArrayList)
        String preu = infoReserva.get(1);
        // mostrar la informaci� de la reserva
        System.out.println("Dades de la reserva:");
        System.out.println("- Codi de reserva: " + codi); 
        System.out.println("- Tipus d'habitaci�: " + tipus); // --> alternativa: + reserves.get(codi).get(0)
        System.out.println("- Preu total: " + preu + "?"); // --> alternativa: + reserves.get(codi).get(1)
        System.out.println("- Serveis addicionals:");
        // si l'ArrayList infoReserva nom�s cont� 2 elements significa que no s'han contractat serveis
        if (infoReserva.size() == 2) { // --> alternativa: + reserves.get(codi).size() == 2
            System.out.println("   -> No s'han contractat serveis addicionals");
        } else {
            // si cont� mes de 2 elements, iterar a partir de la posici� 2 fins l'�ltima per a mostrar els serveis
            for (int i = 2; i <= infoReserva.size()-1; i++) { // --> alternativa: <= reserves.get(codi).size()-1
                System.out.println("   -> " + infoReserva.get(i)); // --> alternativa: + reserves.get(codi).geet(i)
            }
        }
    }
    
    /**
     * Mostra totes les reserves existents per a un tipus d'habitaci� espec�fic
     */
    static void obtindreReservaPerTipus() {
        // si l'hotel no t� reserves, informar de la situaci� i tornar al men�
        if (reserves.isEmpty()) {            
            System.out.println();
            System.out.println("Actualment l'hotel no t� reserves.");
            return;
        }
        // obtindre el tipus d'habitaci�
        System.out.println(); // l�nea de separaci�
        System.out.print("Introdueix un tipus d'habitaci�: 1 (Est�ndard), 2 (Suite) o 3 (Deluxe): ");
        String tipus = seleccionarTipusHabitacio();
        // si la disponibilitat del tipus seleccionat �s menor que la capacitat, tenim reserves d'eixe tipus
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
            // si l'hotel no t� reserves del tipus seleccionat, informar de la situaci�
            System.out.println(); // l�nea de separaci�
            System.out.println("Actualment l'hotel no t� reserves del tipus d'habitaci� " + tipus + ".");
        }
    }
    
    /**
     * Funci� recursiva que mostra les dades de totes les habitacions reservades 
     * d'un tipus especificat per l'usuari
     */
    static void llistarReservesPerTipus(int[] codis, String tipus) {
        // obtindre el primer codi de reserva de l'array de codis
        int codiReserva = codis[0];
        // obtindre l'ArrayList amb la informaci� de la reserva
        ArrayList<String> infoReserva = reserves.get(codiReserva);
        // obtindre el tipus d'habitaci� de la reserva (posici� 0 de l'ArrayList)
        String tipusReserva = infoReserva.get(0);
        // si el tipus d'habitaci� de la reserva coincideix amb tipus, mostrar les dades de la reserva
        if (tipusReserva.equals(tipus)) { // --> alternativa: reserves.get(codis[0]).get(0).equals(tipus)
            mostrarDadesReserva(codiReserva); // --> alternativa: reserves.get(codis[0])
        }
        // si queden mes codis de reserva en l'array (comprovaci� que evita que el m�tode es cride a si mateix indefinidament)
        if (codis.length > 1) {
            // crear un nou array de tamany una unitat menor que codis
            int newCodis[] = new int[codis.length-1];
            // copiar codis en newCodis amb la posici� 0 eliminada
            System.arraycopy(codis, 1, newCodis, 0, newCodis.length);
            // cridada recursiva a llistarReservesPerTipus amb newCodis
            llistarReservesPerTipus(newCodis, tipus);
        }        
    }
    
}


