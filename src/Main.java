
import java.io.*;

import error.ParseException;
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
	private static String checkPath = null;
	private static String testPath = null;
	private static String commands = null;
	private static Options opcionesLineaComandos = null;

	private static void ParseaArgumentos(String[] args) throws ParseException {

		if(args.length == 0)
			throw new ParseException("Main received no arguments\n");

		// define the valid command line options
		//
		Options opcionesLineaComandos = Main.construyeOpciones();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine linea = parser.parse(opcionesLineaComandos, args);
			commands = "";
			parseHelpOption(linea);
			parseDirectoryOption(linea);
			parseTestOption(linea);
			parseInputOption(linea);
			parseOutputOption(linea);
			parseStepsOption(linea);
			parseCheckOption(linea);

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
		opcionesLineacomandos.addOption(Option.builder("t").longOpt("test").hasArg().desc("Test every file from the directory path given").build());
		opcionesLineacomandos.addOption(Option.builder("c").longOpt("check").hasArg().desc("Check every file from the directory path given").build());
		opcionesLineacomandos.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file where the reports are written (console by default)").build());
		opcionesLineacomandos.addOption(Option.builder("s").longOpt("steps").hasArg().desc("Number of simulation steps should follow this option (default ticks number: " + Main.timeLimitPorDefecto + ").").build());

		return opcionesLineacomandos;
	}

	private static void parseHelpOption(CommandLine linea) {
		if (linea.hasOption("h")) {
			commands += "h";
		}
	}

	private static void parseInputOption(CommandLine linea) throws error.ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
		if(Main.ficheroEntrada != null)
			commands += "f";
	}

	private static void parseOutputOption(CommandLine linea) throws error.ParseException {
		Main.ficheroSalida = linea.getOptionValue("o");
		if(Main.ficheroSalida != null)
			commands += "o";
	}

	private static void parseStepsOption(CommandLine linea) throws error.ParseException { String t = linea.getOptionValue("s", Main.timeLimitPorDefecto.toString());
		try {
			Main.timeLimit = Integer.parseInt(t);
			assert (Main.timeLimit < 0);
			if(linea.hasOption("s"))
				commands += "s";
		} catch (Exception e) {
			throw new error.ParseException("Valor invalido para el limite de tiempo: " + t);
		}
	}
	private static void parseDirectoryOption(CommandLine linea)throws error.ParseException {
		Main.directoryPath = linea.getOptionValue("d");
		if(Main.directoryPath != null)
			commands += "d";
	}
	private static void parseTestOption(CommandLine linea) throws error.ParseException{
		Main.testPath = linea.getOptionValue("t");
		if(Main.testPath != null)
			commands += "t";
	}
	private static void parseCheckOption(CommandLine linea) throws error.ParseException{
		Main.checkPath = linea.getOptionValue("c");
		if(Main.checkPath != null)
			commands += "c";
	}


	private static void iniciaModoEstandar() throws IOException {
		InputStream is = new FileInputStream(new File(Main.ficheroEntrada));
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, Main.timeLimit, is, os);
		ctrl.execute();
		is.close();
		System.out.println("File " + Main.ficheroEntrada + " has been executed !\n");
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

		System.out.println("Number of files found to execute: " + files.length);

		for (File file : files) {
			Main.ficheroEntrada = file.getAbsolutePath();
			Main.ficheroSalida = file.getAbsolutePath() + ".out";
			Main.timeLimit = 10;
			Main.iniciaModoEstandar();
		}

	}
	private static  void execute() throws IOException {
		switch (Main.commands){
			case "f": case"fo": case"fs": case"fos":{
				Main.iniciaModoEstandar();
			} break;
			case "d": case"ds":{
				Main.executeFiles(Main.directoryPath);
			} break;
			case "t": case"ts":{
				Main.executeFiles(Main.testPath);
				Check.test(Main.testPath);
			} break;
			case "c":{
				Check.test(Main.checkPath);
			} break;
			case "h":{
				/*HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp(Main.class.getCanonicalName(), opcionesLineaComandos, true);*/
				System.out.print("Available commands:\n-f <file path> (-o <file path>)(-s positive-int)\n-t <directory path> (-s positive-int)\n-d <directory path> (-s positive-int)\n-c <directory path>\n");
			} break;
			default:{
				System.out.println("program arguments not valid");
			}
		}
	}

	public static void main(String[] args) throws IOException {

		// example command lines:
		//
		// -i resources/examples/event/basic/ex1.ini
		// -i resources/examples/event/advanced/ex1.ini
		// --help
		//
		try {
			Main.ParseaArgumentos(args);
			Main.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
