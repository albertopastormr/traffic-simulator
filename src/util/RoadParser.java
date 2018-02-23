package util;

import error.RoadMapException;
import logic.Junction;
import logic.RoadMap;
import logic.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class RoadParser {
    public static List<Junction> JunctionListParse(String[] itinerary, RoadMap map) throws RoadMapException {
        List<Junction> li = new ArrayList<>();
        for(String s : itinerary){
            li.add(map.getJunction(s));
        }

        return li;
    }

    public static List<Vehicle> VehicleListParse(String[] itinerary, RoadMap map) throws RoadMapException {
        List<Vehicle> li = new ArrayList<>();
        for(String s : itinerary){
            li.add(map.getVehicle(s));
        }
        return li;
    }
}
