package logic;


import error.EventException;
import error.RoadMapException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {
    private List<Road> roads;
    private List<Junction> junctions;
    private List<Vehicle> vehicles;

    // Structures for a easier search
    private Map<String, Road> roadMap;
    private Map<String, Junction> junctionMap;
    private Map<String, Vehicle> vehicleMap;


    public RoadMap() {
        this.roads = new ArrayList<Road>();
        this.junctions = new ArrayList<Junction>();
        this.vehicles = new ArrayList<Vehicle>();

        this.roadMap = new HashMap<String, Road>();
        this.junctionMap = new HashMap<String, Junction>();
        this.vehicleMap = new HashMap<String, Vehicle>();
    }

    public void addJunction(String idJunction, Junction junction) throws RoadMapException {
        if(! junctionMap.containsKey(idJunction)){ // if junction doesn't exist , it inserts it in junctionMap
            this.junctions.add(junction);
            this.junctionMap.put(idJunction, junction);
        }
        else
            throw new RoadMapException("The junction" + idJunction + " found already exists in junctionMap (duplicated junction)\n");
    }

    public void addVehicle(String idVehicle,Vehicle vehicle) throws RoadMapException, EventException {
        if(!vehicleMap.containsKey(idVehicle)){
            this.vehicles.add(vehicle);
            this.vehicleMap.put(idVehicle, vehicle);
            vehicle.moveFirstRoad();
        }
        else
            throw new RoadMapException("The vehicle" + idVehicle + " found already exists in vehicleMap (duplicated vehicle)\n");
    }

    public void addRoad(String idRoad, Junction origin, Road road, Junction destination) throws RoadMapException, EventException {
        if(!roadMap.containsKey(idRoad)){
            this.roads.add(road);
            this.roadMap.put(idRoad, road);
            origin.addRoadOutToJunction(destination, road);
            destination.addRoadInToJunction(idRoad, road);
        }
        else
            throw new RoadMapException("The road " + idRoad + " found already exists in the junctionMap (duplicated junction)\n");
    }

    public String generateReport(int time) {
        String report = "";

        for(Junction j : junctions)
            report += j.generateReport(time);
        for(Road r : roads)
            report += r.generateReport(time);
        for(Vehicle v : vehicles)
            report += v.generateReport(time);

        return report;
    }
    public void update() throws EventException {

        for (Junction j : junctions) // Calls advance() for every junction
            j.advance();
        for(Road r : roads) // Calls advance() for every road
            r.advance();

    }

    // returns a junction searched by its id in the map
    public Junction getJunction(String id) throws RoadMapException {
        Junction junction = this.junctionMap.get(id);
        if(junction != null)
            return junction;
        else
            throw new RoadMapException("The junction " + id + "doesn't exist in junctionMap\n");
    }

    // returns a vehicle searched by its id in the map
    public Vehicle getVehicle(String id) throws RoadMapException {
        Vehicle vehicle = vehicleMap.get(id);
        if(vehicle != null)
            return vehicle;
        else
            throw new RoadMapException("The vehicle " + id + " doesn't exist in vehicleMap\n");
    }

    // returns a road searched by its id in the map
    public Road getRoad(String id) throws RoadMapException {
        Road road = roadMap.get(id);
        if(road != null)
            return road;
        else
            throw new RoadMapException("The road " + id + "doesn't exist in roadMap\n");
    }
}
