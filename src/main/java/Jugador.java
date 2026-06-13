public class Jugador {

    //Atributos
    private long idJugador;
    private String nombreJugador;
    private int dorsalJugador;
    private String posicionJugador;
    private int goles;
    private int tarjetasAmarillas;
    private int tarjetasRojas;

    //Contructores
    public Jugador(String nombreJugador, int dorsalJugador, String posicionJugador) {
        this.nombreJugador = nombreJugador;
        this.dorsalJugador = dorsalJugador;
        this.posicionJugador = posicionJugador;
        this.goles = 0;
        this.tarjetasAmarillas = 0;
        this.tarjetasRojas = 0;
    }

    //Getters y Setters
    public String getNombre() {
        return nombreJugador;
    }

    public int getDorsal() {
        return dorsalJugador;
    }

    public String getPosicion() {
        return posicionJugador;
    }

    public int getGoles() {
        return goles;
    }


    //Metodos Propios - Implementación en el futuro
    public void marcarGol() {
        goles++;
    }

    public void recibirTarjetaAmarilla() {
        tarjetasAmarillas++;
    }

    public void recibirTarjetaRoja() {
        tarjetasRojas++;
    }

    @Override
    public String toString() {
        return dorsalJugador + " - " + nombreJugador +
                " | Posición: " + posicionJugador +
                " | Goles: " + goles +
                " | Amarillas: " + tarjetasAmarillas +
                " | Rojas: " + tarjetasRojas;
    }
}