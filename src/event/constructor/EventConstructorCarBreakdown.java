package event.constructor;

import event.Event;
import ini.IniSection;

public class EventConstructorCarBreakdown extends EventConstructor {

    public EventConstructorCarBreakdown() {
        this.tag = "new_CarBreakdown";
        this.keys = new String[] { "time", "id" };
        this.defaultValues = new String[] { "", "", };
    }

    @Override
    public Event parser(IniSection section) {

        if (!section.getTag().equals(this.tag) ||
                section.getValue("type") != null) return null;
        else
            return new EventCarBreakdown(
                    // extrae el valor del campo “time” en la sección
                    // 0 es el valor por defecto en caso de no especificar el tiempo
                    EventConstructor.parseaIntNoNegativo(section, "time", 0),
                    // extrae el valor del campo “id” de la sección
                    EventConstructor.identificadorValido(section, "id")
            );
    }

    @Override
    public String toString() { return "New CarBreakdown"; }
}
