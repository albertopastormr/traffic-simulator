package event.constructor;

import event.Event;
import event.EventNewCongestedJunction;
import ini.IniSection;

public class EventConstructorNewCongestedJunction extends EventConstructor {

    public EventConstructorNewCongestedJunction() {
        this.tag = "new_junction";
        this.keys = new String[] { "time", "id", "type" };
        this.defaultValues = new String[] { "", "", "mc"};
    }

    @Override
    public Event parser(IniSection section) {

        if (!section.getTag().equals(this.tag) ||
                !section.getValue("type").equals("mc")) return null;
        else
            return new EventNewCongestedJunction(
                    // extrae el valor del campo “time” en la sección
                    // 0 es el valor por defecto en caso de no especificar el tiempo
                    EventConstructor.parseIntNoNegative(section, "time", 0),
                    // extrae el valor del campo “id” de la sección
                    EventConstructor.validID(section, "id") );
    }

    @Override
    public String toString() { return "New Congested Junction"; }
}
