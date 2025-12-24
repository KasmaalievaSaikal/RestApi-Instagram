package java19.excepion;

public class AccessIsDeniedException extends RuntimeException {
    public AccessIsDeniedException() {
    }

    public AccessIsDeniedException(String message) {
        super(message);
    }
}
