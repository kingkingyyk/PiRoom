package PiRoom;

import com.pi4j.system.SystemInfo;

public class Utility {

	public static double getPiTemperature() {
		try {
			return SystemInfo.getCpuTemperature();
		} catch (Exception e) {}
		return 0.0;
	}
	
	public static int getPiClockSpeed () {
		try {
			return (int)(SystemInfo.getClockFrequencyArm()/1000000);
		} catch (Exception e) {}
		return 0;
	}

	public static double getPiMemoryUsage () {
		try {
			return (SystemInfo.getMemoryUsed()/(double)SystemInfo.getMemoryTotal())*100;
		} catch (Exception e) {}
		return 0.0;
	}
}
