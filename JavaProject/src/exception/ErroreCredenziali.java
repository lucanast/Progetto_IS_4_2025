package exception;

public class ErroreCredenziali extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ErroreCredenziali() {
        super();
    }

    public ErroreCredenziali(String message) {
        super(message);
    }

    public ErroreCredenziali(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroreCredenziali(Throwable cause) {
        super(cause);
    }
}
