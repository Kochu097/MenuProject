package Menu.MenuBackend.common.exception;

public class DuplicateAuthTokenException extends RuntimeException {
    public DuplicateAuthTokenException(String message) {
        super(message);
    }
}
