package event.constructor;

import event.Event;
import event.EventNewBicJunction;
import ini.IniSection;

public class EventConstructorNewBicJunction extends EventConstructor {

	public EventConstructorNewBicJunction() {
		this.tag = "new_junction";
		this.keys = new String[] { "time", "id", "type", "time_slice"};
		this.defaultValues = new String[] { "", "", "bic",""};
	}

	@Override
	public Event parser(IniSection section) {

		if (!section.getTag().equals(this.tag) ||
				!section.getValue("type").equals("bic")) return null;
		else
			return new EventNewBicJunction(
					// extrae el valor del campo “time” en la sección
					// 0 es el valor por defecto en caso de no especificar el tiempo
					EventConstructor.parseIntNoNegative(section, "time", 0),
					// extrae el valor del campo “id” de la sección
					EventConstructor.validID(section, "id"),
					EventConstructor.parseIntNoNegative(section, "time_slice", 1));
	}

	@Override
	public String toString() { return "New BicJunction"; }
}
