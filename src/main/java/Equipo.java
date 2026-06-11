import ExcepcionesPersonalizadas.ExceptionJugadorInvalido;

import java.util.ArrayList;

public class Equipo {

    //Atributos
    private long idEquipo;
    private String nombre;
    private int puntos;
    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;
    private int golesFavor;
    private int golesContra;
    private ArrayList<Jugador> jugadores;
    private int diferenciaGoles;

    //Constructores
    public Equipo(String nombre, String ciudad) {
        this.nombre = nombre;
        this.puntos = 0;
        this.partidosJugados = 0;
        this.ganados = 0;
        this.empatados = 0;
        this.perdidos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.jugadores = new ArrayList<>();
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getGanados() {
        return ganados;
    }

    public int getEmpatados() {
        return empatados;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public int getPerdidos() {
        return perdidos;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public int getDiferenciaGoles() {
        return golesFavor - golesContra;
    }

    //Metodos Propios
    public void sumarVictoria() {
        puntos += 3;
        ganados++;
        partidosJugados++;
    }

    public void sumarEmpate() {
        puntos += 1;
        empatados++;
        partidosJugados++;
    }

    public void sumarDerrota() {
        perdidos++;
        partidosJugados++;
    }

    public void sumarGoles(int golesFavor, int golesContra) {
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;
    }

    public void añadirJugador(String nombreJugador, int dorsalJugador, String posicionJugador) throws ExceptionJugadorInvalido {
        if(nombreJugador == null || posicionJugador == null || nombreJugador.isBlank()|| posicionJugador.isBlank()) {
            throw new ExceptionJugadorInvalido("Error, no puede estar vacios los campos");
        }
        if (dorsalJugador < 0){
            throw new ExceptionJugadorInvalido("Error, el dorsal no puede ser negativo");
        }
        Jugador jugador = new Jugador(nombreJugador, dorsalJugador, posicionJugador);

        jugadores.add(jugador);
    }

    public void mostrarJugadores() {
        System.out.println("Jugadores de " + nombre + ":");

        for (Jugador jugador : jugadores) {
            System.out.println(jugador);
        }
    }

    public Jugador buscarJugador(String nombreJugador) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombreJugador)) {
                return jugador;
            }
        }

        return null;
    }

    //ToString
    @Override
    public String toString() {
        return nombre + " | Puntos: " + puntos + " | PJ: " + partidosJugados +
                " | G: " + ganados + " | E: " + empatados + " | P: " + perdidos +
                " | GF: " + golesFavor + " | GC: " + golesContra;
    }
}
