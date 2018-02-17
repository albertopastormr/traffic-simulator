package error;

public class SimulationError extends Exception{
    public SimulationError() {
    }

    public SimulationError(String message) {
        super(message);
    }
}
