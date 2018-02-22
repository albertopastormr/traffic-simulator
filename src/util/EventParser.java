package util;

import event.Event;
import event.constructor.*;
import ini.IniSection;

public class EventParser {

    private static EventConstructor[] events = {
            new EventConstructorNewJunction(),
            new EventConstructorNewRoad(),
            new EventConstructorNewVehicle(),
            new EventConstructorBreakdown()
    };


    public static Event EventParse(IniSection sec) {
        // loop iterating EventConstructor parsing with a try-error method
        int i = 0;
        boolean cntinue = true;
        Event e = null;
        while (i < EventParser.events.length && cntinue) {

            e = EventParser.events[i].parser(sec); // Method parser() contained in EventConstructor
            if (e != null) cntinue = false;
            else i++;
        }
        return e;
    }
}
