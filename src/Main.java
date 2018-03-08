
import java.io.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import control.Controller;
import model.TrafficSimulator;


public class Main {


	private final static Integer timeLimitPorDefecto = 10;
	private static Integer timeLimit = null;
	private static String ficheroEntrada = null;
	private static String ficheroSalida = null;
	private static String directoryPath = null;
	
	private static void ParseaArgumentos(String[] args) {

		// define the valid command line options
		//
		Options opcionesLineaComandos = Main.construyeOpciones();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine linea = parser.parse(opcionesLineaComandos, args);
			parseHelpOption(linea, opcionesLineaComandos);
			parseDirectoryOption(linea);
			parseInputOption(linea);
			parseOutputOption(linea);
			parseStepsOption(linea);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] resto = linea.getArgs();
			if (resto.length > 0) {
				String error = "Illegal arguments:";
				for (String o : resto)
					error += (" " + o);
				throw new error.ParseException(error);
			}

		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options construyeOpciones() {
		Options opcionesLineacomandos = new Options();
		opcionesLineacomandos.addOption(Option.builder("d").longOpt("directory").hasArg().desc("Launchs every valid .ini found in a given directory (path)").build());
		opcionesLineacomandos.addOption(Option.builder("h").longOpt("help").desc("Shows every command's description").build());
		opcionesLineacomandos.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events single input file should follow this option").build());
		opcionesLineacomandos.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file where the reports are written (console by default)").build());
		opcionesLineacomandos.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Number of simulation steps should follow this option (default ticks number: " + Main.timeLimitPorDefecto + ").")
				.build());

		return opcionesLineacomandos;
	}

	private static void parseHelpOption(CommandLine linea, Options opcionesLineaComandos) {
		if (linea.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), opcionesLineaComandos, true);
			System.exit(0);
		}
	}

	private static void parseInputOption(CommandLine linea) throws error.ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
		if (Main.ficheroEntrada == null) {
			throw new error.ParseException("Input file doesn't exist\n");
		}
	}

	private static void parseOutputOption(CommandLine linea) throws error.ParseException {
		Main.ficheroSalida = linea.getOptionValue("o");
	}

	private static void parseStepsOption(CommandLine linea) throws error.ParseException { String t = linea.getOptionValue("t", Main.timeLimitPorDefecto.toString());
		try {
			Main.timeLimit = Integer.parseInt(t);
			assert (Main.timeLimit < 0);
		} catch (Exception e) {
			throw new error.ParseException("Valor invalido para el limite de tiempo: " + t);
		}
	}
	private static void parseDirectoryOption(CommandLine linea)throws error.ParseException {
		Main.directoryPath = linea.getOptionValue('d');
		if(Main.directoryPath == null)
			throw new error.ParseException("Directory path doesn't exist\n");
	}
	private static void iniciaModoEstandar() throws IOException {
		InputStream is = new FileInputStream(new File(Main.ficheroEntrada));
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, Main.timeLimit, is, os);
		ctrl.execute();
		is.close();
		System.out.println("Done!");
	}
	private static void executeFiles(String path) throws IOException {

		File dir = new File(path);

		if ( !dir.exists() ) {
			throw new FileNotFoundException(path);
		}

		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});

		for (File file : files) {
			Main.ficheroEntrada = file.getAbsolutePath();
			Main.ficheroSalida = file.getAbsolutePath() + ".out";
			Main.timeLimit = 10;
			Main.iniciaModoEstandar();
		}

	}

	public static void main(String[] args) throws IOException {

		// example command lines:
		//
		// -i resources/examples/event/basic/ex1.ini
		// -i resources/examples/event/advanced/ex1.ini
		// --help
		//
		
		//Main.ParseaArgumentos(args);
		//Main.iniciaModoEstandar();
		Main.executeFiles("examples-out/advanced");
	
	}

}
