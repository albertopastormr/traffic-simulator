package event;

import logic.CongestedJunction;
import logic.GenericJunction;
import logic.Roundabout;

public class EventNewCongestedJunction extends EventNewJunction {

    protected Integer maxValueInterval;
    protected Integer minValueInterval;

    public EventNewCongestedJunction(int time, String id) {
        super(time, id);
    }

    @Override
    protected GenericJunction<?> createJunction() {
        return new CongestedJunction(this.id);
    }

    // . . .

    @Override
    public String toString() {
        return "New CongestedJunction";
    }
}
