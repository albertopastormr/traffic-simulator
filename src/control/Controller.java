package control;

import error.EventException;
import error.NewEventException;
import error.RoadMapException;
import error.SimulationError;
import event.Event;
import ini.Ini;
import ini.IniSection;
import model.TrafficSimulator;
import util.EventParser;
import view.observer.ObserverTrafficSimulator;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Controller {

    private TrafficSimulator simulator;
    private OutputStream outputStream;
    private InputStream fileInput;
    private int simulatorSteps;
    private Thread ctrlExecute;

    public void execute() {
        try {
            this.loadEvent(this.fileInput);
            this.simulator.execute(this.simulatorSteps, this.outputStream);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void execute(int simulationSteps, int milliseconds_to_sleep_execution){
		ctrlExecute = new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while(i < simulationSteps && !Thread.interrupted()) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							try {
								simulator.execute(1, outputStream);
							} catch (SimulationError | RoadMapException | NewEventException | EventException | IOException error) {
								System.out.println(error.getMessage());
								error.printStackTrace();
							}
						}
					});
					try {
						Thread.sleep(milliseconds_to_sleep_execution);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					i++;
				}
				ctrlExecute = null;
			}
		});
		ctrlExecute.start();
    }
    public void sleepExecution(long milliseconds_to_sleep_execution){
		if (ctrlExecute != null) {
			try {
				Thread.sleep(milliseconds_to_sleep_execution);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void stopExecution(){
		if(this.ctrlExecute != null && this.ctrlExecute.isAlive()){
			this.ctrlExecute.interrupt();
		}
	}

    public void reset(){
        this.simulator.reset();
    }

    public Controller(TrafficSimulator simulator, int simulatorSteps, InputStream fileInput,  OutputStream outputStream) {
        this.simulator = simulator;
        this.outputStream = outputStream;
        this.fileInput = fileInput;
        this.simulatorSteps = simulatorSteps;
    }

    public void loadEvent(InputStream inStream) throws SimulationError{
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
            if (e != null)
                this.simulator.insertEvent(e);
            else
                throw new SimulationError("Evento desconocido: " + sec.getTag());
        }
    }

    public String generateReport(){
        return this.simulator.generateReport();
    }
    public void addObserver(ObserverTrafficSimulator obs){
        this.simulator.addObserver(obs);
    }
    public void removeObserver(ObserverTrafficSimulator obs){
        this.simulator.removeObserver(obs);
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
