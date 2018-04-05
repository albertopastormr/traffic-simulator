package event.constructor;

import event.Event;
import event.EventNewBreakdown;
import ini.IniSection;

public class EventConstructorBreakdown extends EventConstructor {

    public EventConstructorBreakdown() {
        this.tag = "make_vehicle_faulty";
        this.keys = new String[] { "time", "duration", "vehicles" };
        this.defaultValues = new String[] { "", "", ""};
    }

    @Override
    public Event parser(IniSection section) {

        if (!section.getTag().equals(this.tag) ||
                section.getValue("type") != null) return null;
        else
            return new EventNewBreakdown(
                    // extrae el valor del campo “time” en la sección
                    // 0 es el valor por defecto en caso de no especificar el tiempo
                    EventConstructor.parseIntNoNegative(section, "time", 0),
                    // extrae el valor del campo “id” de la sección
                    EventConstructor.parseIntNoNegative(section,"duration", 0),
                    section.getValue("vehicles").split(",")
            );
    }

    @Override
    public String toString() { return "New Vehicle Breakdown"; }
}
