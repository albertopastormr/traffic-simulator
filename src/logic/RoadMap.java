package logic;


import java.util.List;
import java.util.Map;

public class RoadMap {
    private List<Road> roads;
    private List<Junction> junctions;
    private List<Vehicle> vehicles;
    // estructuras para agilizar la búsqueda (id,valor)
    private Map<String, Road> roadMap;
    private Map<String, Junction> junctionMap;
    private Map<String, Vehicle> vehicleMap;

    // Por a;adir

    public RoadMap() {
        // inicializa los atributos a sus constructoras por defecto.
        // Para roads, junctions y vehicles puede usarse ArrayList.
        // Para los mapas puede usarse HashMap
    }
    public void addJunction(String idJunction, Junction junction) {
        // comprueba que “idJunction” no existe en el mapa.
        // Si no existe, lo añade a “junctions” y a “JunctionMap”.
        // Si existe lanza una excepción.
    }

    public void addVehicle(String idVehicle,Vehicle vehicle) {
        // comprueba que “idVehicle” no existe en el mapa.
        // Si no existe, lo añade a “Vehicles” y a “VehicleMap”,
        // y posteriormente solicita al Vehicle que se mueva a la siguiente
        // carretera de su itinerario (moveNextRoad).
        // Si existe lanza una excepción.
    }

    public void addRoad(String idRoad,
                             Junction origen,
                             Road carretera,
                             Junction destino) {
        // comprueba que “idRoad” no existe en el mapa.
        // Si no existe, lo añade a “roads” y a “RoadMap”,
        // y posteriormente actualiza los "junctions" origen y destino como sigue:
        // - Añade al cruce origen la carretera, como “RoadIn”
        // - Añade al crude destino la carretera, como “RoadOut”
        // Si existe lanza una excepción.
    }

    public String generateReport(int time) {
        String report = "";
        // genera informe para cruces
        // genera informe para carreteras
        // genera informe para vehiculos
        return report;
    }
    public void actualizar() {
        // llama al método avanza de cada cruce
        // llama al método avanza de cada carretera
    }
    public Junction getJunction(String id) {
        // devuelve el cruce con ese “id” utilizando el JunctionMap.
        // sino existe el cruce lanza excepción.
        return null;
    }
    public Vehicle getVehicle(String id) {
        // devuelve el vehículo con ese “id” utilizando el VehicleMap.
        // sino existe el vehículo lanza excepción.
        return null;
    }
    public Road getRoad(String id) {
        // devuelve la carretera con ese “id” utilizando el RoadMap.
        // sino existe la carretra lanza excepción.
        return null;
    }
}
