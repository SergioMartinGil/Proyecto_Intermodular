package ExcepcionesPersonalizadas;

public class ExceptionJugadorInvalido extends RuntimeException {
    public ExceptionJugadorInvalido(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
