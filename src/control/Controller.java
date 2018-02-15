package control;

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
        this.loadEvent(this.fileInput);
        this.simulator.execute(simulatorSteps,this.fileOutput);
    }

    private void loadEvent(InputStream inStream) {
        Ini ini;
        try {
            // lee el fichero y carga su atributo iniSections
            ini = new Ini(inStream);
        }
        catch (IOException e) {
            throw new ErrorDeSimulacion("Error en la lectura de eventos: " + e);
        }
        // recorremos todas los elementos de iniSections para generar el evento
        // correspondiente
        for (IniSection sec : ini.getSections()) {
            // parseamos la sección para ver a que evento corresponde
            Event e = EventParser.EventParse(sec);
            if (e != null) this.simulador.insertaEvento(e);
            else
                throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
        }
    }

}
