package kg.nurtelecom.internlabs.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse taskNotFoundExceptionHandler(TaskNotFoundException taskNotFoundException) {
        return new ErrorResponse(taskNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse projectNotFoundException(ProjectNotFoundException projectNotFoundException) {
        return new ErrorResponse(projectNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badPageArgumentException(BadPageArgumentException badPageArgumentException) {
        return new ErrorResponse(badPageArgumentException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidProjectOperationException(InvalidProjectOperationException invalidProjectOperationException) {
        return new ErrorResponse(invalidProjectOperationException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        String errorMessage = Objects.requireNonNull(methodArgumentNotValidException.getBindingResult().getFieldError()).getDefaultMessage();
        return new ErrorResponse(errorMessage);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userGroupNotFoundException(UserGroupNotFoundException userGroupNotFoundException) {
        return new ErrorResponse(userGroupNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidUserGroupOperationException(InvalidUserGroupOperationException invalidUserGroupOperationException) {
        return new ErrorResponse(invalidUserGroupOperationException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFoundException(UserNotFoundException userNotFoundException) {
        return new ErrorResponse(userNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notificationTemplateNotFoundException(NotificationTemplateNotFoundException notificationTemplateNotFoundException) {
        return new ErrorResponse(notificationTemplateNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse recipientNotFoundException(RecipientNotFoundException recipientNotFoundException) {
        return new ErrorResponse(recipientNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse projectConstrainsException (ProjectConstraintsException projectConstraintsException) {
        return new ErrorResponse(projectConstraintsException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse usernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        return new ErrorResponse(usernameNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse emailNotFoundException(EmailNotFoundException emailNotFoundException) {
        return new ErrorResponse(emailNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse roleNotFoundException(RoleNotFoundException roleNotFoundException) {
        return new ErrorResponse(roleNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public  ErrorResponse tokenRefreshException(RefreshTokenNotFoundException tokenRefreshException) {
        return new ErrorResponse(tokenRefreshException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ErrorResponse emailVerificationException(EmailVerificationException emailVerificationException) {
        return new ErrorResponse(emailVerificationException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ErrorResponse imageSaveException(ImageSaveException imageSaveException) {
        return new ErrorResponse(imageSaveException.getMessage());
    }

}
