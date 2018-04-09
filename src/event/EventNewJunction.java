package event;

import error.RoadMapException;
import logic.GenericJunction;
import logic.Junction;
import logic.RoadMap;
import util.RoadParser;

import java.util.List;

public class EventNewJunction extends Event {
    protected String id;

    public EventNewJunction(int time, String id) {
        super(time);
        this.id = id;
    }
    @Override
    public void execute(RoadMap map) throws RoadMapException {
        map.addJunction(this.id, this.createJunction());
    }

    protected GenericJunction<?> createJunction(){
        return new Junction(this.id);
    }

    @Override
    public String toString() {
        return "New Junction";
    }
}