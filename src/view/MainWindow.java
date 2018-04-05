package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import javafx.scene.control.ToolBar;
import logic.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

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
    static private final String[] columnIdVehicle = {"ID", "Road", "Location", "Speed", "Km.", "Breakdown Time", "Itinerary"};
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

            }

            @Override
            public void windowClosing(WindowEvent e) {
                // pendiente
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        JPanel mainPanel = this.createMainPanel();
        this.setContentPane(mainPanel);

        // BARRA ESTADO INFERIOR
        // (contiene una JLabel param mostrar el estado del simulador)
        this.addStatusBar;

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

        // BARRA DE HERRAMIENTAS
        this.addToolBar(mainPanel);

        // FILE CHOOSER
        this.fileChooser = new JFileChooser();

        // REPORT DIALOG
        this.reportsDialog = new ReportsDialog(this, this.controller);
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
        String text = "";
        try{
            text = this.readFile(this.actualFile);
        }catch(FileNotFoundException e){
            this.actualFile = null;
            this.showErrorDialog("ERROR: file read did not work " + e.getMessage());
        }
        topPanel.setLayout(new BoxLayout( centralPanel, BoxLayout.X_AXIS)); // pendiente revision
        // No es observador
        this.panelEventsEditor = new EventsEditorPanel(this.actualFile.getName(), text, true,this);
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
        bottomPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.X_AXIS));
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
    private void createMainPanel(){
        JPanel mainPanel = new JPanel();


    }

    public void loadFile(){
    	int returnValue = this.fileChooser.showOpenDialog(null);
    	if(returnValue == JFileChooser.APPROVE_OPTION){
    		File file = this.fileChooser.getSelectedFile();
    		try{
    			String str = readFile(file);
    			this.controller.reset();
    			this.actualFile = file;
    			this.panelEventsEditor.setText(s);
    			this.panelEventsEditor.setBorder(this.actualFile.getName());
    			this.panelStatusBar.setMessage("File " + file.getName() + " of events loaded into the editor");
			}
			catch (FileNotFoundException e){
    			this.showErrorDialog("ERROR: file read did not work " + e.getMessage());
			}
		}
	}

    @Override
    public void simulatorError(int time, RoadMap map, List<event.Event> event, SimulationError e) {
		// PENDIENTE
    }

    @Override
    public void advance(int time, RoadMap map, List<event.Event> event) {
		// PENDIENTE
    }

    @Override
    public void addEvent(int time, RoadMap map, List<event.Event> event) {
		// PENDIENTE
    }

    @Override
    public void reset(int time, RoadMap map, List<event.Event> event) {
		// PENDIENTE
    }

    public void showErrorDialog(String str){
        JOptionPane.showMessageDialog(this,str);
    }
}
