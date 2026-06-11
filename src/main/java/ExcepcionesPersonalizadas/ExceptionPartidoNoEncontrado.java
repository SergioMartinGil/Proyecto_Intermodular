package ExcepcionesPersonalizadas;

public class ExceptionPartidoNoEncontrado extends RuntimeException {
    public ExceptionPartidoNoEncontrado(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
