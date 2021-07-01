package main.exception;

public class InsufficientFundsException extends Exception {

    private static final long serialVersionUID = -6576779337906482877L;

    public InsufficientFundsException(String message) {
        super(message);
    }
}
