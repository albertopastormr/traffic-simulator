package view;

import control.Controller;
import error.SimulationError;
import javafx.scene.control.ToolBar;
import logic.GenericJunction;
import logic.Road;
import logic.RoadMap;
import logic.Vehicle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
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
    private ToolBarPanel panelToolbar;

    // INFERIOR PANEL
    static private final String[] columnIdVehicle = {"ID", "Road", "Location", "Speed", "Km.", "Breakdown Time", "Itinerary"};
    static private final String[] columnIdRoad = {"ID", "Origin", "Destination", "Length", "Max. Speed", "Vehicles"};
    static private final String[] columnIdJunction = {"ID", "Green", "Red"};

    private TablePanel<Vehicle> panelVehicles;
    private TablePanel<Road> panelRoads;
    private TablePanel<GenericJunction> panelJunctions;

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
    private JPanel createTopPanel(JPanel centralPanel){
        JPanel topPanel = new JPanel();

        return topPanel;
    }
    private JPanel createBottomPanel(JPanel centralPanel){
        JPanel bottomPanel = new JPanel();

        return bottomPanel;
    }
    private JPanel createMainPanel(){
        JPanel mainPanel = new JPanel();

        return mainPanel;
    }

    @Override
    public void simulatorError(int time, RoadMap map, List<event.Event> event, SimulationError e) {

    }

    @Override
    public void advance(int time, RoadMap map, List<event.Event> event) {

    }

    @Override
    public void addEvent(int time, RoadMap map, List<event.Event> event) {

    }

    @Override
    public void reset(int time, RoadMap map, List<event.Event> event) {

    }
}
