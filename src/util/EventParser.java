package util;

import event.Event;
import event.constructor.*;
import ini.IniSection;

public class EventParser {

    private static EventConstructor[] events = {
            new EventConstructorNewJunction(),
            new EventConstructorNewRoad(),
            new EventConstructorNewVehicle(),
            new EventConstructorCarBreakdown()
    };
    // bucle de prueba y error
    public static Event EventParse(IniSection sec) {
        int i = 0;
        boolean cntinue = true;
        Event e = null;
        while (i < EventParser.events.length && cntinue) {
            // ConstructorEventos contiene el método parse(sec)
            e = EventParser.events[i].parser(sec);
            if (e != null) cntinue = false;
            else i++;
        }
        return e;
    }
}
