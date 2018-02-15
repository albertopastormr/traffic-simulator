package model;

import event.Event;
import logic.RoadMap;
import util.SortedArrayList;

import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;

public class TrafficSimulator {

    private RoadMap map;
    private List<Event> events;
    private int timeCount;

    public TrafficSimulator() {
        this.map = new RoadMap();
        this.timeCount = 0;
        Comparator<Event> cmp = new Comparator<Event>() {
            // Por completar
            @Override
            public int compare(Event o1, Event o2) {
                return 0;
            }
        };
        this.events = new SortedArrayList<>(cmp); // estructura ordenada por “tiempo”
    }

    public void execute(int simulationStep, OutputStream fileOutput) {

        int timeLimit = this.timeCount + simulationStep - 1;
        while (this.timeCount <= timeLimit) {
            // ejecutar todos los eventos correspondienes a “this.contadorTiempo”
            // actualizar “mapa”
            // escribir el informe en “fileOutput”, controlando que no sea null.
        }
    }
    public void insertaEvento(Event e){

        // inserta un evento en “eventos”, controlando que el tiempo de
        // ejecución del evento sea menor que “contadorTiempo”
    }
}

