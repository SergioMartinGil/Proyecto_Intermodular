package ExcepcionesPersonalizadas;

public class ExcepcionTorneoInvalido extends RuntimeException {
    public ExcepcionTorneoInvalido(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
