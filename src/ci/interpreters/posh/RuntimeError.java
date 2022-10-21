package ci.interpreters.posh;

public class RuntimeError extends RuntimeException {
    final token token;

    RuntimeError(token token, String message) {
        super(message);
        this.token = token;
    }
}
