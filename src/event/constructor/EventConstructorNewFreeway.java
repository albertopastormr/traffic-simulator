package event.constructor;

import event.Event;
import event.EventNewFreeway;
import ini.IniSection;

public class EventConstructorNewFreeway extends EventConstructor {

    public EventConstructorNewFreeway() {
        this.tag = "new_road";
        this.keys = new String[] { "time", "id", "type", "max_speed", "length", "src", "dest","lanes"};
        this.defaultValues = new String[] { "", "", "lanes", "","","","",""};
    }

    @Override
    public Event parser(IniSection section) {

        if (!section.getTag().equals(this.tag) ||
                !section.getValue("type").equals("lanes")) return null;
        else
            return new EventNewFreeway(
                    // extrae el valor del campo “time” en la sección
                    // 0 es el valor por defecto en caso de no especificar el tiempo
                    EventConstructor.parseIntNoNegative(section, "time", 0),
                    // extrae el valor del campo “id” de la sección
                    EventConstructor.validID(section, "id"),
                    EventConstructor.parseIntNoNegative(section,"max_speed", 0),
                    EventConstructor.parseIntNoNegative(section, "length", 0),
                    section.getValue("src"),
                    section.getValue("dest"),
                    EventConstructor.parseIntNoNegative(section, "lanes", 1));
    }

    @Override
    public String toString() { return "New Freeway"; }
}
