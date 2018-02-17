package logic;

import ini.IniSection;

import java.util.List;

public class Road  extends SimulationObject {

    private String id;
    private int length;
    private int speedMax;
    private Junction origin;
    private Junction destiny;
    private List<Vehicle> vehicles;

    public Road(String id) {
        super(id);
    }


    public void inVehicle(){

    }
    public void outVehicle(){

    }

    @Override
    public void completeSectionDetails(IniSection is) {

    }

    @Override
    public String getSectionName() {
        return null;
    }

    public void advance(){

    }
}
