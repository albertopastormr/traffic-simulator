package util;

import error.RoadMapException;
import logic.Junction;
import logic.RoadMap;

import java.util.List;

public class RoadParser {
    public static List<Junction> JunctionListParse(String[] itinerary, RoadMap map) throws RoadMapException {
        List<Junction> li = null;

        for(String s : itinerary){
            li.add(map.getJunction(s));
        }

        return li;
    }
}
