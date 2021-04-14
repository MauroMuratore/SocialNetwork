package lib.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Log {

	private static File file = new File("data/log.txt");
	private static File error = new File("data/error.txt");

	private static String writeMessageLog(Class classname, String logToWrite) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String data = formatter.format(calendar.getTime());
		String nameclass = classname.getName();
		String log = data+ " "  + nameclass + " " + logToWrite + "\n";
		return log;
	}
	
	public static void writeRoutineLog(Class classname, String logToWrite) {
		String log = writeMessageLog(classname, logToWrite);
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, true);
			fw.write(log);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		System.out.println(log);
	}
	
	public static void writeErrorLog(Class classname, String logToWrite) {
		String log = writeMessageLog(classname, logToWrite);
		FileWriter fw = null;
		try {
			fw = new FileWriter(error, true);
			fw.write(log);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		System.err.println(log);
	}
	



}
