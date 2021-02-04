package pl.pjatk.fooddeli.exception;

public class ValidationException extends Exception {
    public ValidationException (String err){
        super(err);
    }
}