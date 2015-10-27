package test.xml;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer {
	
	private Long beginTime;
	
	public void begin() {
		beginTime = System.currentTimeMillis();
		printTime(false);
	}
	
	public void end() {
		printTime(true);
		System.out.println("Cost time: " + (System.currentTimeMillis() - beginTime));
	}
	
	private void printTime(boolean end) {
		String indicator = end ?
				"End at:   " :
				"Start at: ";
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		System.out.println(indicator + dateFormat.format(new Date()));
	}
	
}
