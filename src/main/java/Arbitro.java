public class Arbitro {

    //Atributos
    private long idArbitro;
    private String nombre;
    private int edad;
    private int partidosArbitrados;
    private int tarjetasMostradas;

    //Constructores
    public Arbitro(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.partidosArbitrados = 0;
        this.tarjetasMostradas = 0;
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getIdArbitro() {
        return idArbitro;
    }

    public void setIdArbitro(long idArbitro) {
        this.idArbitro = idArbitro;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getTarjetasMostradas() {
        return tarjetasMostradas;
    }

    public void setTarjetasMostradas(int tarjetasMostradas) {
        this.tarjetasMostradas = tarjetasMostradas;
    }

    public int getPartidosArbitrados() {
        return partidosArbitrados;
    }

    public void setPartidosArbitrados(int partidosArbitrados) {
        this.partidosArbitrados = partidosArbitrados;
    }

    //Metodos Propios
    public void sumarPartidoArbitrado() {
        partidosArbitrados++;
    }
    public void mostrarTarjetas() {
        tarjetasMostradas++;
    }

    //ToString

    @Override
    public String toString() {
        return "Árbitro: " + nombre +
                " | Edad: " + edad +
                " | Partidos arbitrados: " + partidosArbitrados +
                " | Tarjetas mostradas: " + tarjetasMostradas;
    }
}
