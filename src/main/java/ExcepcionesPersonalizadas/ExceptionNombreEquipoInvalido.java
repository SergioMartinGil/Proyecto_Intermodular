package ExcepcionesPersonalizadas;

public class ExceptionNombreEquipoInvalido extends RuntimeException {
    public ExceptionNombreEquipoInvalido(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
