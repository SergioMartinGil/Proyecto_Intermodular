import ExcepcionesPersonalizadas.ExceptionEquipoNoEncontrado;
import ExcepcionesPersonalizadas.ExceptionNombreEquipoInvalido;
import ExcepcionesPersonalizadas.ExceptionPartidoNoEncontrado;

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
    public Torneo( String nombre, String temporada){
        this.idTorneo = cuentaIds+1;
        this.nombre = nombre;
        this.temporada = temporada;
        this.equipos = new ArrayList<>();
        this.partidos = new ArrayList<>();
        this.arbitros = new ArrayList<>();
    }

    //Getters y Setters

    //Metodos propios

    public void añadirEquipo(String nombreEquipo, String ciudad) throws ExceptionNombreEquipoInvalido {
        if (nombreEquipo == null || nombreEquipo.isBlank()){
            throw new ExceptionNombreEquipoInvalido("Error, el nombre no puede estar vacio");
        }
        if (nombreEquipo.matches(".*\\d.*")){
            throw new ExceptionNombreEquipoInvalido("Error, no puede contener numeros");
        }

        Equipo equipo = new Equipo(nombreEquipo, ciudad);
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
    public void crearPartido(String nombreLocal, String nombreVisitante) throws ExceptionPartidoNoEncontrado {
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
    public void mostrarTablaPuntos() {

        equipos.sort((e1, e2) -> {
            if (e2.getPuntos() != e1.getPuntos()) {
                return e2.getPuntos() - e1.getPuntos();
            }

            return e2.getDiferenciaGoles() - e1.getDiferenciaGoles();
        });

        System.out.println("===========================================================================");
        System.out.printf("%-5s %-20s %-5s %-5s %-5s %-5s %-5s %-5s %-5s %-5s%n",
                "Pos", "Club", "PJ", "G", "E", "P", "GF", "GC", "DG", "Pts");
        System.out.println("===========================================================================");

        for (int i = 0; i < equipos.size(); i++) {
            Equipo equipo = equipos.get(i);

            System.out.printf("%-5d %-20s %-5d %-5d %-5d %-5d %-5d %-5d %-5d %-5d%n",
                    i + 1,
                    equipo.getNombre(),
                    equipo.getPartidosJugados(),
                    equipo.getGanados(),
                    equipo.getEmpatados(),
                    equipo.getPerdidos(),
                    equipo.getGolesFavor(),
                    equipo.getGolesContra(),
                    equipo.getDiferenciaGoles(),
                    equipo.getPuntos());
        }

        System.out.println("===========================================================================");
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
