package kg.nurtelecom.internlabs.taskmanager.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String msg) {
        super(msg);
    }
}
