package control;

import error.SimulationError;
import event.Event;
import ini.Ini;
import ini.IniSection;
import model.TrafficSimulator;
import util.EventParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Controller {

    private TrafficSimulator simulator;
    private OutputStream fileOutput;
    private InputStream fileInput;
    private int simulatorSteps;

    public void execute() {
        try {
            this.loadEvent(this.fileInput);
            this.simulator.execute(simulatorSteps, this.fileOutput);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Controller(TrafficSimulator simulator, int simulatorSteps, InputStream fileInput,  OutputStream fileOutput) {
        this.simulator = simulator;
        this.fileOutput = fileOutput;
        this.fileInput = fileInput;
        this.simulatorSteps = simulatorSteps;
    }

    private void loadEvent(InputStream inStream) throws SimulationError{
        Ini ini;
        try {
            // lee el fichero y carga su atributo iniSections
            ini = new Ini(inStream);
        }
        catch (IOException e) {
            throw new SimulationError("Error en la lectura de eventos: " + e);
        }
        // recorremos todas los elementos de iniSections para generar el evento
        // correspondiente
        for (IniSection sec : ini.getSections()) {
            // parseamos la sección para ver a que evento corresponde
            Event e = EventParser.EventParse(sec);
            if (e != null) this.simulator.insertEvent(e);
            else
                throw new SimulationError("Evento desconocido: " + sec.getTag());
        }
    }

}
