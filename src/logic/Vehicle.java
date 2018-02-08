package logic;

import java.util.ArrayList;

public class Vehicle{

    private String id;
    private float kilometrage;
    private float speedMax;
    private float speedActual;
    private Road roadActual;
    private float locationActual;
    private ArrayList<Junction> junctions;
    private int breakdownTime;
    private boolean destiny;

    public void setSpeedActual(float speedActual) {
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


    public String generateReport() {
        return "[vehicle_report]\n" +
                "id = " + id +
                "\nkilometrage = " + kilometrage +
                "\nspeedActual = " + speedActual +
                "\nlocationActual = " + locationActual +
                "\nbreakdownTime = " + breakdownTime +
                '}';
    }
}
