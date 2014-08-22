package minefuse.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.entity.Player;

public class FileLoggingUtils {

	
	public static void savePunishments(Player target, Player from, String punishment, String reason) {
		
		        String path = "plugins/MineFuse/reports.log";
		        DateFormat time = new SimpleDateFormat("dd.MM HH:mm:ss");
		        Calendar calendar = Calendar.getInstance();
		        String toWrite = "(" + time.format(calendar.getTime()) + ") " + from.getName() + "" + punishment + " the player " + target.getName() + " for: " + reason;
		        saveToFile(path, toWrite);
		    }

	private static void saveToFile(String path, String toWrite) {
	        BufferedWriter bw = null;
	        try {
	            bw = new BufferedWriter(new FileWriter(path, true));
	            bw.write(toWrite);
	            bw.newLine();
	        } catch (Exception localException) {
	            try {
	                if (bw != null) {
	                    bw.flush();
	                    bw.close();
	                }
	            } catch (Exception localException1) {

	            }
	        } finally {
	            try {
	                if (bw != null) {
	                    bw.flush();
	                    bw.close();
	                }
	            } catch (Exception localException2) {
	            }
	        }
	    }
}