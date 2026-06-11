public class Principal {
    public static void main(String[] args) {
        System.out.println("Proyecto de futboleros");

        Torneo torneo = new Torneo("Copa Campeones", "2025-2026");

        torneo.añadirEquipo("Barcelona", "Barcelona");
        torneo.añadirEquipo("Real Madrid", "Madrid");
        torneo.añadirEquipo("Atlético Madrid", "Madrid");

        torneo.crearPartido("Barcelona", "Real Madrid");
        torneo.crearPartido("Barcelona", "Atlético Madrid");
        torneo.crearPartido("Real Madrid", "Atlético Madrid");

        torneo.introducirResultado(1, 2, 1);
        torneo.introducirResultado(2, 3, 0);
        torneo.introducirResultado(3, 1, 1);

        torneo.mostrarTablaPuntos();

        torneo.añadirJugadorAEquipo("Barcelona", "Pedri", 8, "Centrocampista");
        torneo.añadirJugadorAEquipo("Barcelona", "Lewandowski", 9, "Delantero");
        torneo.añadirJugadorAEquipo("Barcelona", "Hylan Karcenty", 7, "Delantero");

        torneo.mostrarJugadoresEquipo("Barcelona");

        Torneo t1 = new Torneo("Liga", "2025");
        t1.añadirEquipo("123", "maracaibo");


    }
}
