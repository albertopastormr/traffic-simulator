package event.constructor;

import event.Event;
import event.EventNewTank;
import ini.IniSection;

public class EventConstructorNewTank extends EventConstructor {

	public EventConstructorNewTank() {
		this.tag = "new_vehicle";
		this.keys = new String[] { "time", "id" ,"type", "max_speed", "itinerary", "number_missiles"};
		this.defaultValues = new String[] { "", "","tank","","",""};
	}

	@Override
	public Event parser(IniSection section) {

		if (!section.getTag().equals(this.tag) || !section.getValue("type").equals("tank"))
			return null;
		else
			return new EventNewTank(
					// extrae el valor del campo “time” en la sección
					// 0 es el valor por defecto en caso de no especificar el tiempo
					EventConstructor.parseIntNoNegative(section, "time", 0),
					// extrae el valor del campo “id” de la sección
					EventConstructor.validID(section, "id"),
					EventConstructor.parseIntNoNegative(section, "max_speed", 0),
					section.getValue("itinerary").split(","),
					EventConstructor.parseIntNoNegative(section, "number_missiles", 0));
	}

	@Override
	public String toString() { return "New Tank"; }
}
