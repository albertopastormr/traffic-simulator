package event.constructor;

import event.Event;
import event.EventNewCar;
import ini.IniSection;

public class EventConstructorNewCar extends EventConstructor {

    public EventConstructorNewCar() {
        this.tag = "new_vehicle";
        this.keys = new String[] { "time", "id" , "max_speed", "itinerary"};
        this.defaultValues = new String[] { "", "", };
    }

    @Override
    public Event parser(IniSection section) {

        if (!section.getTag().equals(this.tag) || section.getValue("type") != null)
            return null;
        else
            return new EventNewCar(
                    // extrae el valor del campo “time” en la sección
                    // 0 es el valor por defecto en caso de no especificar el tiempo
                    EventConstructor.parseIntNoNegative(section, "time", 0),
                    // extrae el valor del campo “id” de la sección
                    EventConstructor.validID(section, "id"),
                    EventConstructor.parseIntNoNegative(section, "max_speed", 0),
                    section.getValue("itinerary").split(","),
                    EventConstructor.parseIntNoNegative(section, "resistance", 0),
                    Double.parseDouble(section.getValue("fault_probability")),
                    EventConstructor.parseIntNoNegative(section, "max_fault_duration" , 0),
                    Long.parseLong(section.getValue("seed")) );
    }

    @Override
    public String toString() { return "New Car"; } // Por completar
}
