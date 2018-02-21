package logic;

import ini.IniSection;

import java.util.List;

public class Vehicle extends SimulationObject {

    private int kilometrage;
    private int speedMax;
    private int speedActual;
    private Road roadActual;
    private int locationActual;
    private List<Junction> itinerary;
    private int breakdownTime;
    private boolean destination;
    private int timeBreak;


    public Vehicle(String id, int speedMax, List<Junction> itinerary) {
        super(id);

    }

    public int getTimeBreak() {
        return timeBreak;
    }

    public int getLocationActual() {
        return this.locationActual;
    }

    public void setSpeedActual(int speedActual) {
        if (speedActual > this.speedMax)
            this.speedActual = this.speedMax;
        else
            this.speedActual = speedActual;
    }

    public void setBreakdownTime(int breakdownTime) {
        if (this.breakdownTime > 0)
            this.breakdownTime += breakdownTime;
        else
            this.breakdownTime = breakdownTime;
    }

    public void advance(){
        if (this.breakdownTime > 0)
            this.breakdownTime--;
        else{
            // Cambiar localizacion
        }
    }

    public void moveNextRoad(){

    }


    public String generateReport(int time) {
        return "[vehicle_report]\n" +
                "id = " + this.id +
                "\nkilometrage = " + kilometrage +
                "\nspeedActual = " + speedActual +
                "\ntime = " + time +
                "\nlocationActual = " + locationActual +
                "\nbreakdownTime = " + breakdownTime +
                '}';
    }

    @Override
    public void completeSectionDetails(IniSection is) {
        // Por completar
        is.setValue("location", this.destination ? "arrived" : this.roadActual + ":" + this.getLocationActual());
    }

    @Override
    public String getSectionName() {
        return "vehicle_report";
    }


}
