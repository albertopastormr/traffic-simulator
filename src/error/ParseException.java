package error;

public class ParseException extends Exception{
    public ParseException() {
    }

    public ParseException(String message) {
        super(message);
    }
}
