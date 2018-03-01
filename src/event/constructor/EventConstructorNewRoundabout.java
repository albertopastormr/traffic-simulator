package event.constructor;

import event.Event;
import event.EventNewRoundabout;
import ini.IniSection;

public class EventConstructorNewRoundabout extends EventConstructor {

    public EventConstructorNewRoundabout() {
        this.tag = "new_junction";
        this.keys = new String[] { "time", "id" };
        this.defaultValues = new String[] { "", "", };
    }

    @Override
    public Event parser(IniSection section) {

        if (!section.getTag().equals(this.tag) ||
                section.getValue("type") != null) return null;
        else
            return new EventNewRoundabout(
                    // extrae el valor del campo “time” en la sección
                    // 0 es el valor por defecto en caso de no especificar el tiempo
                    EventConstructor.parseIntNoNegative(section, "time", 0),
                    // extrae el valor del campo “id” de la sección
                    EventConstructor.validID(section, "id"),
                    EventConstructor.parseIntNoNegative(section, "min_time_slice", 0),
                    EventConstructor.parseIntNoNegative(section, "max_time_slice", 1));
    }

    @Override
    public String toString() { return "New Roundabout"; }
}
