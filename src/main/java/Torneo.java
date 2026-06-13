import ExcepcionesPersonalizadas.ExcepcionTorneoInvalido;
import ExcepcionesPersonalizadas.ExceptionEquipoNoEncontrado;
import ExcepcionesPersonalizadas.ExceptionNombreEquipoInvalido;
import ExcepcionesPersonalizadas.ExceptionPartidoNoEncontrado;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Torneo {

    //Atributos
    private long idTorneo;
    private long cuentaIds = 0;
    private String nombre;
    private String temporada;
    private ArrayList<Equipo> equipos;
    private ArrayList<Partido> partidos;
    private ArrayList<Arbitro> arbitros;

    //Contructores
    public Torneo(String nombre){
        this.nombre = nombre;
    }
    public Torneo( String nombre, String temporada){
        this.idTorneo = cuentaIds+1;
        this.nombre = nombre;
        this.temporada = temporada;
        this.equipos = new ArrayList<>();
        this.partidos = new ArrayList<>();
        this.arbitros = new ArrayList<>();
    }

    //Getters y Setters

    public long getIdTorneo() {
        return idTorneo;
    }

    public long getCuentaIds() {
        return cuentaIds;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTemporada() {
        return temporada;
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }
    public ArrayList<Partido> getPartidos() {
        return partidos;
    }
    public ArrayList<Arbitro> getArbitros() {
        return arbitros;
    }

    //Metodos propios
    public static Torneo crearTorneo(String nombre, String temporada)
            throws ExcepcionTorneoInvalido {

        if (nombre == null || nombre.isBlank()) {
            throw new ExcepcionTorneoInvalido("El nombre del torneo no puede estar vacío.");
        }
        if (nombre.matches(".*\\d.*")) {
            throw new ExcepcionTorneoInvalido("El nombre del torneo no puede contener números.");
        }
        if (temporada == null || temporada.isBlank()) {
            throw new ExcepcionTorneoInvalido("La temporada no puede estar vacía.");
        }

        return new Torneo(nombre, temporada);
    }

    public void añadirEquipo(String nombreEquipo) throws ExceptionNombreEquipoInvalido {
        if (nombreEquipo == null || nombreEquipo.isBlank()){
            throw new ExceptionNombreEquipoInvalido("Error, el nombre no puede estar vacio");
        }
        if (nombreEquipo.matches(".*\\d.*")){
            throw new ExceptionNombreEquipoInvalido("Error, no puede contener numeros");
        }

        Equipo equipo = new Equipo(nombreEquipo);
        equipos.add(equipo);
    }
    public Equipo mostrarEquipos(String nombreEquipo) throws ExceptionEquipoNoEncontrado{
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombreEquipo)) {
                return equipo;
            }
        }
        throw new ExceptionEquipoNoEncontrado("No se ha encontrado el equipo: " + nombreEquipo);
    }
    public void crearPartido(String nombreLocal, String nombreVisitante)  throws ExceptionPartidoNoEncontrado, ExceptionEquipoNoEncontrado {
        Equipo equipoLocal = mostrarEquipos(nombreLocal);
        Equipo equipoVisitante = mostrarEquipos(nombreVisitante);

        if (equipoLocal == null) {
            throw new ExceptionPartidoNoEncontrado("Error, no existe el equipo local " + nombreLocal);
        }

        if (equipoVisitante == null) {

            throw new ExceptionPartidoNoEncontrado("No existe el equipo visitante " + nombreVisitante);
        }

        if (equipoLocal == equipoVisitante) {

            throw new ExceptionPartidoNoEncontrado("Un equipo no puede jugar contra si mismo");
        }

        Partido partido = new Partido(equipoLocal, equipoVisitante);
        partidos.add(partido);

        System.out.println("Partido creado: " + equipoLocal.getNombre() + " vs " + equipoVisitante.getNombre());
    }
    public void mostrarClasificacion() {

        System.out.println("==============================================");
        System.out.println("TORNEO: " + nombre);
        System.out.println("TEMPORADA: " + temporada);
        System.out.println("==============================================");

        equipos.sort((a, b) -> {
            if (b.getPuntos() != a.getPuntos()) {
                return b.getPuntos() - a.getPuntos();
            }
            return b.getDiferenciaGoles() - a.getDiferenciaGoles();
        });

        for (Equipo equipo : equipos) {
            System.out.println(
                    equipo.getNombre() +
                            " | Pts: " + equipo.getPuntos() +
                            " | PJ: " + equipo.getPartidosJugados() +
                            " | G: " + equipo.getGanados() +
                            " | E: " + equipo.getEmpatados() +
                            " | P: " + equipo.getPerdidos() +
                            " | GF: " + equipo.getGolesFavor() +
                            " | GC: " + equipo.getGolesContra()
            );
        }
    }
    public void introducirResultado(int numeroPartido, int golesLocal, int golesVisitante) {

        if (numeroPartido < 1 || numeroPartido > partidos.size()) {
            System.out.println("Número de partido incorrecto.");
            return;
        }

        if (golesLocal < 0 || golesVisitante < 0) {
            System.out.println("Los goles no pueden ser negativos.");
            return;
        }

        Partido partido = partidos.get(numeroPartido - 1);

        partido.introducirResultado(golesLocal, golesVisitante);

        System.out.println("Resultado introducido correctamente.");
        System.out.println(partido);
    }
    public void añadirJugadorAEquipo(String nombreEquipo, String nombreJugador, int dorsal, String posicion) {
        Equipo equipo = mostrarEquipos(nombreEquipo);

        if (equipo == null) {
            System.out.println("No existe el equipo: " + nombreEquipo);
            return;
        }

        equipo.añadirJugador(nombreJugador, dorsal, posicion);
        System.out.println("Jugador añadido correctamente al equipo " + equipo.getNombre());
    }
    public void mostrarJugadoresEquipo(String nombreEquipo) {
        Equipo equipo = mostrarEquipos(nombreEquipo);

        if (equipo == null) {
            System.out.println("No existe el equipo: " + nombreEquipo);
            return;
        }

        equipo.mostrarJugadores();
    }
    public void añadirArbitro(String nombre, int edad) {
        Arbitro arbitro = new Arbitro(nombre, edad);
        arbitros.add(arbitro);

        System.out.println("Árbitro añadido correctamente.");
    }
    public Arbitro buscarArbitro(String nombreArbitro) {
        for (Arbitro arbitro : arbitros) {
            if (arbitro.getNombre().equalsIgnoreCase(nombreArbitro)) {
                return arbitro;
            }
        }

        return null;
    }
    public void asignarArbitroAPartido(int numeroPartido, String nombreArbitro) {
        if (numeroPartido < 1 || numeroPartido > partidos.size()) {
            System.out.println("Número de partido incorrecto.");
            return;
        }

        Arbitro arbitro = buscarArbitro(nombreArbitro);

        if (arbitro == null) {
            System.out.println("No existe el árbitro: " + nombreArbitro);
            return;
        }

        Partido partido = partidos.get(numeroPartido - 1);
        partido.asignarArbitro(arbitro);

        System.out.println("Árbitro asignado correctamente.");
    }
        //ToString
    @Override
    public String toString() {
        return "Torneo{" +
                "idTorneo=" + idTorneo +
                ", nombre='" + nombre + '\'' +
                ", temporada=" + temporada +
                '}';
    }
}
