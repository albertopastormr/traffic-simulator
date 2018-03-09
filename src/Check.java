import ini.Ini;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Check {

	/**
	 * This method run the simulator on all files that ends with .ini if the given
	 * path, and compares that output to the expected output. It assumes that for
	 * example "example.ini" the expected output is stored in "example.ini.eout".
	 * The simulator's output will be stored in "example.ini.out"
	 * 
	 * @throws IOException
	 */


	public static void test(String path) throws IOException {

		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});

		System.out.println(files.length);

		for (File file : files) {
			test(file.getAbsolutePath(), file.getAbsolutePath() + ".out", file.getAbsolutePath() + ".eout");
			System.out.println(file.getAbsolutePath());
		}

	}

	private static void test(String inFile, String outFile, String expectedOutFile) throws IOException {
		boolean equalOutput = (new Ini(outFile)).equals(new Ini(expectedOutFile));
		System.out.println("Resultado para: '" + inFile + "' : "
				+ (equalOutput ? "OK!" : ("distinto a la salida esperada +'" + expectedOutFile + "'")));
	}


	public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {
		test("examples-out/advanced");
	}

}

