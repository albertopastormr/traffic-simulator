package error;

public class NewEventException extends Exception{
    public NewEventException() {
        super();
    }

    public NewEventException(String message) {
        super(message);
    }
}
