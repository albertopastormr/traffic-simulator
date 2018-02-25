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
        this.kilometrage = 0;
        this.isAtJunction = false;
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
            if(!this.isAtJunction){
                this.locationActual += this.speedActual;
                this.kilometrage += this.speedActual;
                if(this.locationActual >= this.roadActual.length){
                    this.kilometrage = this.kilometrage - this.locationActual + this.roadActual.length;
                    this.locationActual = this.roadActual.length;
                    this.roadActual.vehicleToJunction(this);
                    this.isAtJunction = true;
                }
            }
        }
    }

    public void moveNextRoad() throws EventException {
        if(this.roadActual != null){
          this.roadActual.outVehicle(this);
          if(  this.roadActual.destination.id.equals(this.itinerary.get(this.itinerary.size() - 1).id) ){
            this.destination = true;
            this.roadActual = null;
            this.speedActual = 0;
            this.locationActual = 0;
          }
          else{
              int indexItineraryActualRoad = this.itinerary.indexOf(this.roadActual.destination);
              Junction destinationJunctionOfNextRoad = this.itinerary.get(indexItineraryActualRoad + 1);
              Road nextRoad = this.roadActual.destination.roadToJunction( destinationJunctionOfNextRoad );

              if(nextRoad != null){
                  nextRoad.inVehicle(this);
                  this.roadActual = nextRoad;
                  this.locationActual = 0;
              }
              else
                  throw new EventException("Next road for vehicle" + this.id + " from road( " + this.roadActual.id + ")\n");
              this.isAtJunction = false;
          }
        }
        else
            throw new EventException("Can't move to next road vehicle " + this.id + "because its road it's null\n");
    }

    public void moveFirstRoad() throws EventException {
        Junction origin = this.itinerary.get(0);
        Junction destination = this.itinerary.get(1);
        Road firstRoad = origin.roadToJunction(destination);
        if(firstRoad != null){
            firstRoad.inVehicle(this);
            this.roadActual = firstRoad;
            this.locationActual = 0;
            this.kilometrage = 0;
        }
        else
            throw new EventException("Can't initialize first road for vehicle " + this.id + "because there's no road connected between first vehicle's junctions\n");
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

    public int getBreakdownTime() {
        return this.breakdownTime;
    }
}
