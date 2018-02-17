
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

	
	private static void ParseaArgumentos(String[] args) {

		// define the valid command line options
		//
		Options opcionesLineaComandos = Main.construyeOpciones();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine linea = parser.parse(opcionesLineaComandos, args);
			parseaOpcionHELP(linea, opcionesLineaComandos);
			parseaOpcionFicheroIN(linea);
			parseaOpcionFicheroOUT(linea);
			parseaOpcionSTEPS(linea);

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

		opcionesLineacomandos.addOption(Option.builder("h").longOpt("help").desc("Muestra la ayuda.").build());
		opcionesLineacomandos.addOption(Option.builder("i").longOpt("input").hasArg().desc("Fichero de entrada de eventos.").build());
		opcionesLineacomandos.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Fichero de salida, donde se escriben los informes.").build());
		opcionesLineacomandos.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Pasos que ejecuta el simulador en su bucle principal (el valor por defecto es " + Main.timeLimitPorDefecto + ").")
				.build());

		return opcionesLineacomandos;
	}

	private static void parseaOpcionHELP(CommandLine linea, Options opcionesLineaComandos) {
		if (linea.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), opcionesLineaComandos, true);
			System.exit(0);
		}
	}

	private static void parseaOpcionFicheroIN(CommandLine linea) throws error.ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
		if (Main.ficheroEntrada == null) {
			throw new error.ParseException("El fichero de eventos no existe");
		}
	}

	private static void parseaOpcionFicheroOUT(CommandLine linea) throws error.ParseException {
		Main.ficheroSalida = linea.getOptionValue("o");
	}

	private static void parseaOpcionSTEPS(CommandLine linea) throws error.ParseException {
		String t = linea.getOptionValue("t", Main.timeLimitPorDefecto.toString());
		try {
			Main.timeLimit = Integer.parseInt(t);
			assert (Main.timeLimit < 0);
		} catch (Exception e) {
			throw new error.ParseException("Valor invalido para el limite de tiempo: " + t);
		}
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

	public static void main(String[] args) throws IOException {

		// example command lines:
		//
		// -i resources/examples/event/basic/ex1.ini
		// -i resources/examples/event/advanced/ex1.ini
		// --help
		//
		
		Main.ParseaArgumentos(args);
		Main.iniciaModoEstandar();
	
	}

}
