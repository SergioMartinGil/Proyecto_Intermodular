package ExcepcionesPersonalizadas;

public class ExceptionEquipoNoEncontrado extends RuntimeException {
    public ExceptionEquipoNoEncontrado(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
