package exception;

public class EmailInUso extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailInUso() {
        super();
    }

    public EmailInUso(String message) {
        super(message);
    }

    public EmailInUso(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailInUso(Throwable cause) {
        super(cause);
    }
}
