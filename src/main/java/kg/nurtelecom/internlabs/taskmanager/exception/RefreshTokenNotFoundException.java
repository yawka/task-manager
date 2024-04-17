package kg.nurtelecom.internlabs.taskmanager.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String msg) {
        super(msg);
    }

    public RefreshTokenNotFoundException(String token, String msg) {
        super(msg);
    }
}
