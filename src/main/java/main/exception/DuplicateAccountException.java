package main.exception;

public class DuplicateAccountException extends Exception {

    private static final long serialVersionUID = -6576779337906482877L;

    public DuplicateAccountException(String message) {
        super(message);
    }
}
