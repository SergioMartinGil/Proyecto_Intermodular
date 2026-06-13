public class Partido {

    //Atributos

    private long idPartido;
    private String fecha;
    private int golesLocal;
    private int golesVisitante;
    private Arbitro nombreArbitro;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private boolean jugado;
    private Arbitro arbitro;

    //Constructores


    public Partido() {
    }

    public Partido(Equipo equipoLocal, Equipo equipoVisitante) {
        this.nombreArbitro = nombreArbitro;
        this.equipoVisitante = equipoVisitante;
        this.equipoLocal = equipoLocal;
        idPartido++;
        this.jugado = false;
    }


    //Getters y Setters

    public long getIdPartido() {
        return idPartido;
    }

    public String getFecha() {
        return fecha;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public Arbitro getNombreArbitro() {
        return nombreArbitro;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    //Metodos Propios

    public void introducirResultado(int golesLocal, int golesVisitante) {

        if (jugado) {
            System.out.println("Este partido ya tiene resultado.");
            return;
        }

        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.jugado = true;

        equipoLocal.sumarGoles(golesLocal, golesVisitante);
        equipoVisitante.sumarGoles(golesVisitante, golesLocal);

        if (golesLocal > golesVisitante) {
            equipoLocal.sumarVictoria();
            equipoVisitante.sumarDerrota();
        } else if (golesLocal < golesVisitante) {
            equipoVisitante.sumarVictoria();
            equipoLocal.sumarDerrota();
        } else {
            equipoLocal.sumarEmpate();
            equipoVisitante.sumarEmpate();
        }
    }

    public void asignarArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
        arbitro.sumarPartidoArbitrado();
    }

    //toString
    @Override
    public String toString() {
        String infoPartido;

        if (jugado) {
            infoPartido = equipoLocal.getNombre() + " " + golesLocal +
                    " - " + golesVisitante + " " +
                    equipoVisitante.getNombre();
        } else {
            infoPartido = equipoLocal.getNombre() + " vs " + equipoVisitante.getNombre();
        }

        if (arbitro != null) {
            infoPartido += " | Árbitro: " + arbitro.getNombre();
        } else {
            infoPartido += " | Árbitro: sin asignar";
        }

        return infoPartido;
    }
}
