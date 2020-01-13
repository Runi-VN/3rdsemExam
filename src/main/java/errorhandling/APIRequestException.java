package errorhandling;

public class APIRequestException extends Exception {

    public APIRequestException(String message) {
        super(message);
    }

    public APIRequestException() {
        super("Could not retrieve requested object from resource");
    }
}
