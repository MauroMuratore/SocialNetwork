package lib.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Log {

	private static File file;
	private static File error; 
	private static int verbose=1;

	public static final int TOP_PRIORITY = 0;
	public static final int HIGH_PRIORITY = 1;
	public static final int MEDIUM_PRIORITY = 2;
	public static final int LOW_PRIORITY =3;

	private static String writeMessageLog(Class classname, String logToWrite) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String data = formatter.format(calendar.getTime());
		String nameclass = classname.getName();
		String log = data+ " "  + nameclass + " " + logToWrite + "\n";
		return log;
	}

	public static void writeRoutineLog(Class classname, String logToWrite, int v) {
		String log = writeMessageLog(classname, logToWrite);
		if(v<2) {
			FileWriter fw = null;
			file =new File("data/log.txt");
			if(!file.exists()) {
				file = new File("../data/log.txt");
			}
			try {
				fw = new FileWriter(file, true);
				fw.write(log);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(v<=verbose) {
			System.out.println(log);
		}
	}

	public static void writeErrorLog(Class classname, String logToWrite) {
		String log = writeMessageLog(classname, logToWrite);
		FileWriter fw = null;
		error = new File("data/error.txt");
		if(!error.exists()) {
			error = new File("../data/error.txt");
		}
		try {
			fw = new FileWriter(error, true);
			fw.write(log);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		System.err.println(log);
	}

	public static void setVerbose(int v) {
		if(v>=0 && v<4) {
			verbose = v;
			Log.writeRoutineLog(Log.class, "cambiato verbosita a " + v, 0);
		}
	}



}
