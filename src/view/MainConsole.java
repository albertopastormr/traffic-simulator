package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;
import view.observer.ObserverTrafficSimulator;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class MainConsole implements ObserverTrafficSimulator {

	private Controller controller;
	private Scanner scanner;
	private InputStream inputStream;

	public MainConsole(Controller controller, InputStream inputStream) {

		controller.addObserver(this);
		this.controller = controller;
		this.scanner = new Scanner(System.in);
		this.inputStream = inputStream;
	}

	public void init(){
		System.out.println("--- MENU ---\n0. EXIT\n1. Execute current file selected\n2. Change current file selected\n3. Remove vehicle\n4. Show all simulation objects");
		int optionSelected = scanner.nextInt();
		while(optionSelected != 0){
			switch	(optionSelected){
				case 1:{
					if(inputStream == null)
						System.out.println("File not selected!");
					else
						controller.execute();
				} break;
				case 2:{
					System.out.println("Type a valid file path");
					String filePath = scanner.next();
					try {
						InputStream inputStream = this.inputStream = new FileInputStream(new File(filePath));
						controller.setInputStream(inputStream);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} break;
				case 3:{
					System.out.println("Type the vehicle to remove");
					String vehicleIdToRemove = scanner.next();
					controller.removeVehicle(vehicleIdToRemove);
					System.out.println("Vehicle " + vehicleIdToRemove + " removed from the simulation!");
				} break;
				case 4:{
					System.out.println(controller.showAll() + "\n");
				} break;
			}

			System.out.println("\n\n--- MENU ---\n0. EXIT\n1. Execute current file selected\n2. Change current file selected\n3. Remove vehicle\n4. Show all simulation objects");
			optionSelected = scanner.nextInt();
		}
		System.out.println("ByeBye !");
	}
	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {
		System.out.println("ERROR at time " + time	+ "\n");
	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		// empty
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		// empty
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		System.out.println("Reset done !\n");
	}

	@Override
	public void removeEvent(int time, RoadMap map, List<Event> events) {
		// empty
	}
}
