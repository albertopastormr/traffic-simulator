package event.constructor;

import event.Event;
import event.EventNewRoad;
import ini.IniSection;

public class EventConstructorNewRoad extends EventConstructor {

    public EventConstructorNewRoad() {
        this.tag = "new_road";
        this.keys = new String[] { "time", "id", "max_speed", "length", "src", "dest" };
        this.defaultValues = new String[] { "", "", "","","",""};
    }

    @Override
    public Event parser(IniSection section) {

        if (!section.getTag().equals(this.tag) ||
                section.getValue("type") != null) return null;
        else
            return new EventNewRoad(
                    // extrae el valor del campo “time” en la sección
                    // 0 es el valor por defecto en caso de no especificar el tiempo
                    EventConstructor.parseIntNoNegative(section, "time", 0),
                    // extrae el valor del campo “id” de la sección
                    EventConstructor.validID(section, "id"),
                    EventConstructor.parseIntNoNegative(section,"max_speed", 0),
                    EventConstructor.parseIntNoNegative(section, "length", 0),
                    section.getValue("src"),
                    section.getValue("dest"));
    }

    @Override
    public String toString() { return "New Road"; }
}
