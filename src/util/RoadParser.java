package util;

import error.RoadMapException;
import logic.GenericJunction;
import logic.RoadMap;
import logic.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class RoadParser {
    public static List<GenericJunction<?>> JunctionListParse(String[] itinerary, RoadMap map) throws RoadMapException {
        List<GenericJunction<?> > li = new ArrayList<>();
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
