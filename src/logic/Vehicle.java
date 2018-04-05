package logic;

import error.EventException;
import event.Event;
import ini.IniSection;

import java.util.List;

public class Vehicle extends SimulationObject {

    protected int kilometrage;
    protected int speedMax;
    protected int speedActual;
    protected Road roadActual;
    protected int locationActual;
    protected List<GenericJunction<?>> itinerary;
    protected int breakdownTime;
    protected boolean destination;
    protected boolean isAtJunction;
    protected int indexActualJunction;


    public Vehicle(String id, int speedMax, List<GenericJunction<?>> itinerary) throws EventException {
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
        this.indexActualJunction = 0;
    }


    public int getLocationActual() {
        return this.locationActual;
    }

    public void setSpeedActual(int speedActual) {
        if(this.breakdownTime > 0)
            this.speedActual = 0;
        else{
            if (speedActual > this.speedMax)
                this.speedActual = this.speedMax;
            else
                this.speedActual = speedActual;
        }

    }

    public void setBreakdownTime(int breakdownTime) {
        if (this.breakdownTime > 0)
            this.breakdownTime += breakdownTime;
        else
            this.breakdownTime = breakdownTime;
        this.speedActual = 0;
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
                    this.speedActual = 0;
                    this.roadActual.vehicleToJunction(this);
                    this.isAtJunction = true;
                }
            }
            else
                this.speedActual = 0;
        }
    }

    public void moveNextRoad() throws EventException {
        if(this.roadActual != null){
          this.roadActual.outVehicle(this);
          if(  !this.destination && this.indexActualJunction  + 2 == this.itinerary.size()){
            this.destination = true;
            this.roadActual = null;
            this.speedActual = 0;
            this.locationActual = 0;
          }
          else{
              GenericJunction<?> destinationJunctionOfNextRoad = this.itinerary.get(this.indexActualJunction + 2);
              Road nextRoad = this.roadActual.destination.roadToJunction( destinationJunctionOfNextRoad );

              if(nextRoad != null){
                  this.indexActualJunction++;
                  this.speedActual = 0;
                  this.locationActual = 0;
                  nextRoad.inVehicle(this);
                  this.roadActual = nextRoad;
              }
              else
                  throw new EventException("Next road for vehicle " + this.id + " from road( " + this.roadActual.id + ")\n");
              this.isAtJunction = false;
          }
        }
        else
            throw new EventException("Can't move to next road vehicle " + this.id + " because its road it's null\n");
    }

    public void moveFirstRoad() throws EventException {
        GenericJunction<?> origin = this.itinerary.get(0);
        GenericJunction<?> destination = this.itinerary.get(1);
        Road firstRoad = origin.roadToJunction(destination);
        if(firstRoad != null){
            firstRoad.inVehicle(this);
            this.indexActualJunction = 0;
            this.roadActual = firstRoad;
            this.locationActual = 0;
            this.kilometrage = 0;
            this.isAtJunction = false;
        }
        else
            throw new EventException("Can't initialize first road for vehicle " + this.id + " because there's no road connected between first vehicle's junctions\n");
    }


    @Override
    public void completeSectionDetails(IniSection is) {
        is.setValue("speed", this.speedActual);
        is.setValue("kilometrage", this.kilometrage);
        is.setValue("faulty", this.breakdownTime );
        is.setValue("location", this.destination ? "arrived" : "(" + this.roadActual + "," + this.getLocationActual() + ")");
    }

    @Override
    public String getSectionName() {
        return "vehicle_report";
    }

    public int getBreakdownTime() {
        return this.breakdownTime;
    }

    public Road getRoadActual() {
        return roadActual;
    }

    public boolean isDestination() {
        return destination;
    }


    @Override
    public String toString() {
        return "(" + this.id + "," + this.locationActual + ")";
    }
}
