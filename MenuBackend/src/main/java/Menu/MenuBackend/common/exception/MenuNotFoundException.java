package Menu.MenuBackend.common.exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException(String message) {
        super(message);
    }
}
