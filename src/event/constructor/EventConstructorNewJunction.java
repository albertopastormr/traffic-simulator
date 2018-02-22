package event.constructor;

import event.Event;
import event.EventNewJunction;
import ini.IniSection;

public class EventConstructorNewJunction extends EventConstructor {

    public EventConstructorNewJunction() {
        this.tag = "new_junction";
        this.keys = new String[] { "time", "id" };
        this.defaultValues = new String[] { "", "", };
    }

    @Override
    public Event parser(IniSection section) {

        if (!section.getTag().equals(this.tag) ||
                section.getValue("type") != null) return null;
        else
            return new EventNewJunction(
                    // extrae el valor del campo “time” en la sección
                    // 0 es el valor por defecto en caso de no especificar el tiempo
                    EventConstructor.parseIntNoNegative(section, "time", 0),
                    // extrae el valor del campo “id” de la sección
                    EventConstructor.validID(section, "id") );
    }

    @Override
    public String toString() { return "New Junction"; }
}
