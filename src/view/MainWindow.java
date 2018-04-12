package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.*;
import view.TableModel.EventsTableModel;
import view.TableModel.JunctionsTableModel;
import view.TableModel.RoadsTableModel;
import view.TableModel.VehiclesTableModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class MainWindow extends JFrame implements ObserverTrafficSimulator {

    public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

    // SUPERIOR PANEL
    static private final String[] columnIdEvents = {"#", "Tiempo", "Tipo"};

    private TextAreaPanel panelEventsEditor;
    private TextAreaPanel panelReports;
    private TablePanel<Event> panelEventsQueue;

    // MENU AND TOOL BAR
    private JFileChooser fileChooser;
    private ToolBar toolBar;

    // GRAPHIC PANEL
    private MapComponent mapComponent;

    // STATUS BAR (INFO AT THE BOTTON OF THE WINDOW)
    private StatusBarPanel panelStatusBar;

    // INFERIOR PANEL
    static private final String[] columnIdVehicle = {"ID", "Road", "Location", "Speed", "Km.", "Brdwn. Time", "Itinerary"};
    static private final String[] columnIdRoad = {"ID", "Origin", "Destination", "Length", "Max. Speed", "Vehicles"};
    static private final String[] columnIdJunction = {"ID", "Green", "Red"};

    private TablePanel<Vehicle> panelVehicles;
    private TablePanel<Road> panelRoads;
    private TablePanel<GenericJunction<?>> panelJunctions;

    // REPORT DIALOG
    private ReportsDialog reportsDialog;

    // MODEL PART - VIEW CONTROLLER MODEL
    private File actualFile;
    private Controller controller;

    public MainWindow(String inputFile, Controller controller){
        super("Traffic Simulator");
        this.controller = controller;
        this.actualFile = (inputFile != null ? new File(inputFile) : null);
        this.initGUI();
        controller.addObserver(this);
    }

    private void initGUI(){
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
				// empty, its not necessary to be implemented
            }

            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }

            @Override
            public void windowClosed(WindowEvent e) {
				// empty, its not necessary to be implemented
            }

            @Override
            public void windowIconified(WindowEvent e) {
				// empty, its not necessary to be implemented
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
				// empty, its not necessary to be implemented
            }

            @Override
            public void windowActivated(WindowEvent e) {
				// empty, its not necessary to be implemented
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
				// empty, its not necessary to be implemented
            }
        });
        JPanel mainPanel = this.createMainPanel();
        this.setContentPane(mainPanel);

        // BARRA DE HERRAMIENTAS
        this.addToolBar(mainPanel);

        // PANEL QUE CONTIENE EL RESTO DE COMPONENTES
        // (Lo dividimos en dos paneles (superior e inferior))
        JPanel centralPanel = this.createCentralPanel();
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        // PANEL SUPERIOR
        this.createTopPanel(centralPanel);

        // MENU
        MenuBar menuBar = new MenuBar(this, this.controller);
        this.setJMenuBar(menuBar);

        // PANEL INFERIOR
        this.createBottomPanel(centralPanel);

        // BARRA ESTADO INFERIOR
        // (contiene una JLabel param mostrar el estado del simulador)
        this.addStatusBar(mainPanel);

        // FILE CHOOSER
        this.fileChooser = new JFileChooser();

        // REPORT DIALOG
        this.reportsDialog = new ReportsDialog(this, this.controller);
        this.setVisibleReportsDialog(false);
        this.pack();
        this.setVisible(true);
    }

    private JPanel createCentralPanel(){
        JPanel centralPanel = new JPanel();
        // Para colocar topPanel y bottomPanel
        centralPanel.setLayout(new GridLayout(2,1));
        return centralPanel;
    }
    private void createTopPanel(JPanel centralPanel){
        JPanel topPanel = new JPanel();
        String text = "", inputName = (this.actualFile != null ? this.actualFile.getName() : "none" );
        try{
            text = ( this.actualFile != null ? this.readFile(this.actualFile) : "");
        }catch(FileNotFoundException e){
            this.actualFile = null;
            this.showErrorDialog("ERROR: file read did not work " + e.getMessage());
        }
        topPanel.setLayout(new BoxLayout( topPanel, BoxLayout.X_AXIS)); // pendiente revision
        // No es observador
        this.panelEventsEditor = new EventsEditorPanel(inputName, text, true,this);
        // Es observador
        this.panelEventsQueue = new TablePanel<Event>("Events Queue: ", new EventsTableModel(MainWindow.columnIdEvents, this.controller));
        // Es observador
        this.panelReports = new ReportsPanel("Reports: ", false,this.controller);

        topPanel.add(this.panelEventsEditor);
        topPanel.add(this.panelEventsQueue);
        topPanel.add(this.panelReports);
        centralPanel.add(topPanel);
    }
    private void createBottomPanel(JPanel centralPanel){
        JPanel bottomPanel = new JPanel(), tablesPanel = new JPanel(), graphicPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        tablesPanel.setLayout(new GridLayout(3, 1));
        graphicPanel.setLayout(new GridLayout(1,1));
        // Es observador
        this.panelVehicles = new TablePanel<Vehicle>("Vehicles", new VehiclesTableModel(MainWindow.columnIdVehicle, this.controller));
        // Es observador
        this.panelRoads = new TablePanel<Road>("Roads", new RoadsTableModel(MainWindow.columnIdRoad, this.controller));
        // Es observador
        this.panelJunctions = new TablePanel<GenericJunction<?>>("Junctions", new JunctionsTableModel(MainWindow.columnIdJunction, this.controller));
        tablesPanel.add(this.panelVehicles);
        tablesPanel.add(this.panelRoads);
        tablesPanel.add(this.panelJunctions);
        // Mapa para los graficos ( dibujo )
        this.mapComponent = new MapComponent(this.controller);
        // Este mapComponent se inserta en el panel inferior
		// Es observador
        graphicPanel.add(new JScrollPane(this.mapComponent, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        bottomPanel.add(tablesPanel);
        bottomPanel.add(graphicPanel);
        centralPanel.add(bottomPanel);
    }
    private JPanel createMainPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		return mainPanel;
    }


    @Override
    public void simulatorError(int time, RoadMap map, List<event.Event> event, SimulationError e) {
		this.showErrorDialog(e.getMessage());
    }

    @Override
    public void advance(int time, RoadMap map, List<event.Event> event) {
		// Advance no observadores
		this.panelStatusBar.setMessage("Advance executed with time " + time);

    }

    @Override
    public void addEvent(int time, RoadMap map, List<event.Event> event) {
		// AddEvent no observadores
    }

    @Override
    public void reset(int time, RoadMap map, List<event.Event> event) {
    	// Reset no observadores
		this.actualFile = null;
		this.panelReports.clear();
    }

    @Override
    public void removeEvent(int time, RoadMap map, List<Event> events) {
        // empty
    }

    public void showErrorDialog(String str){
        JOptionPane.showMessageDialog(this,str);
    }
    public void showDialog(String str){ JOptionPane.showMessageDialog(this, str);}

    public void loadFile(){
    	int returnValue = this.fileChooser.showOpenDialog(null);
    	if(returnValue == JFileChooser.APPROVE_OPTION){
    		File file = this.fileChooser.getSelectedFile();
    		try{
    			String str = readFile(file);
    			this.actualFile = file;
    			this.panelEventsEditor.setText(str);
    			this.panelEventsEditor.setBorder(this.actualFile.getName());
    			this.panelStatusBar.setMessage("File " + file.getName() + " of events loaded into the editor");
			}
			catch (FileNotFoundException e){
    			this.showErrorDialog("ERROR: file read did not work " + e.getMessage());
			}
		}
	}
    public String readFile(File file) throws FileNotFoundException {
		return new Scanner(file).useDelimiter("\\A").next();
	}
	public void saveEventsEditor(){
		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			writeFile(file, this.panelEventsEditor.getText());
		}
    }
	public static void writeFile(File file, String content) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void saveReports(){
		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			writeFile(file, this.panelReports.getText());
		}
	}
	protected void execute(){
		this.controller.execute(this.getSteps());
	}
	public void resetAll(){
		this.actualFile = null;
		this.controller.reset();
		this.panelEventsEditor.clear();
		this.panelReports.clear();
		this.panelStatusBar.setMessage("Reset done!");
		this.showDialog("Reset done!");
		this.loadFile();
	}
	protected void exit(){
		int n = JOptionPane.showOptionDialog(this,
				"Are sure you want to exit?", "Exit",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null,
				null, null);
		if (n == 0)
			System.exit(0);
	}
	public void generateReport(){
        this.panelReports.setText(this.controller.generateReport());
	}
	public void generateSelectedItemsReport(){
		String report = "";

		for(GenericJunction<?> j : this.reportsDialog.getSelectedJunctions())
			report += j.generateReport(this.toolBar.getTime());
		for(Road r : this.reportsDialog.getSelectedRoads())
			report += r.generateReport(this.toolBar.getTime());
		for(Vehicle v : this.reportsDialog.getSelectedVehicles())
			report += v.generateReport(this.toolBar.getTime());

		this.panelReports.setText(report);
	}
	public void clearReports(){
        this.panelReports.clear();
    }
	public void clearEventsEditor(){
		this.panelEventsEditor.clear();
	}
	public int getSteps(){
		return this.toolBar.getSteps();
	}
	public String getEventsEditorText(){
		return this.panelEventsEditor.getText();
	}
	public void setMessage(String str){
		this.showDialog(str);
	}
	public void setVisibleReportsDialog(boolean visible){ this.reportsDialog.setVisible(visible);}

	private void addStatusBar(JPanel mainPanel){
		this.panelStatusBar = new StatusBarPanel("Welcome to the Traffic Simulator !", this.controller);
		mainPanel.add(this.panelStatusBar, BorderLayout.PAGE_END);
	}
	private void addToolBar(JPanel panel){
		this.toolBar = new ToolBar(this, this.controller);
		panel.add(this.toolBar);
	}
	protected void insertAtEventsEditor(String str){
		this.panelEventsEditor.insert(str);
	}
}
