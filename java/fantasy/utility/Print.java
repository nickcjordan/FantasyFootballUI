package fantasy.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Print {

	public static void error(String message) {
		System.err.println("[ERROR]\t" + message);
	}
	
	public static void info(String message) {
		System.out.println("[INFO]\t" + message);
	}
	
	public static void debug(String message) {
		System.err.println("[DEBUG]\t" + message);
	}

	public static void debug(int i) {
		System.err.println("[DEBUG]\t" + i);
	}

	public static void dashed() {
		System.out.println("    +--------------------------------------+");
	}
	
	public static void dashed2() {
		System.out.println("\t    +--------------------------------------+");
	}
	
	public static void logDraftResults(String draftBoard) throws IOException {
		File file = getProperFile("draftLog.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
		out.write(draftBoard);
		out.close();
	}
	
	public static File getProperFile(String fileName) {
		String path = System.getProperty("user.dir");
		File loc = new File(path + "\\src\\main\\" + fileName);
		File locLinux = new File(path + "/" + fileName);
		File locWindows = new File(path + "\\" + fileName);
		return loc.exists() ? loc : locLinux.exists() ? locLinux : locWindows.exists() ? locWindows : new File(fileName);
	}
}
