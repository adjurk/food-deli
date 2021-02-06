package pl.pjatk.fooddeli.exception;

public class OrderValidationException extends Exception {
    public OrderValidationException(String err) {
        super(err);
    }
}