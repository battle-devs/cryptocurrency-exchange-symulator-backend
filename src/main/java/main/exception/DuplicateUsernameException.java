package main.exception;

public class DuplicateUsernameException extends Exception {

    private static final long serialVersionUID = -6576779337906482877L;

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
