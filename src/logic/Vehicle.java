package logic;

import error.EventException;
import event.Event;
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
    private boolean isAtJunction;


    public Vehicle(String id, int speedMax, List<Junction> itinerary) throws EventException {
        super(id);
        if(speedMax >= 0)
            this.speedMax = speedMax;
        else
            throw new EventException("Argument 'SpeedMax' isn't greater or equals than 0\n");

        if(itinerary.size() >= 2)
            this.itinerary = itinerary;
        else
            throw new EventException("Argument 'itinerary' doesn't have a minimum of 2 junctions\n");

        this.roadActual = null;
        this.speedActual = 0;
        this.locationActual = 0;
        this.destination = false;
        this.breakdownTime = 0;
        this.timeBreak = 0;
        this.kilometrage = 0;
        this.isAtJunction = false;
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
            if(!this.isAtJunction){
                this.locationActual++;
                this.kilometrage++;
                if(this.locationActual >= this.roadActual.length){
                    this.locationActual = this.roadActual.length;
                    // Actualizar el kilometraje para corregirlo
                    this.isAtJunction = true;
                }
            }
        }
    }

    public void moveNextRoad(){
        if(this.roadActual != null){
          this.roadActual.vehicles.remove(this);
          if(  this.roadActual.destination.id.equals(this.itinerary.get(this.itinerary.size() - 1).id) ){
            this.destination = true;
            this.roadActual = null;
            this.speedActual = 0;
            this.locationActual = 0;
          }
          else{

          }
        }
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
