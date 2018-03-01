package event;

import logic.GenericJunction;
import logic.Roundabout;

public class EventNewRoundabout extends EventNewJunction {

    protected Integer maxValueInterval;
    protected Integer minValueInterval;

    public EventNewRoundabout(int time, String id, int minValueInterval, int maxValueInterval) {
        super(time, id);
        this.maxValueInterval = maxValueInterval;
        this.minValueInterval = minValueInterval;
    }

    @Override
    protected GenericJunction<?> createJunction() {
        return new Roundabout(this.id, this.minValueInterval, this.maxValueInterval);
    }

    // . . .

    @Override
    public String toString() {
        return "New Roundabout";
    }
}
