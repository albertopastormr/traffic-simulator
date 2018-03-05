package event.constructor;

import event.Event;
import event.EventNewPath;
import ini.IniSection;

public class EventConstructorNewPath extends EventConstructor {

    public EventConstructorNewPath() {
        this.tag = "new_road";
        this.keys = new String[] { "time", "id" };
        this.defaultValues = new String[] { "", "", };
    }

    @Override
    public Event parser(IniSection section) {

        if (!section.getTag().equals(this.tag) ||
                !section.getValue("type").equals("dirt")) return null;
        else
            return new EventNewPath(
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
    public String toString() { return "New Path"; }
}
