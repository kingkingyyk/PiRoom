package PiRoom;

import com.pi4j.system.SystemInfo;

public class Utility {

	public static double getPiTemperature() {
		try {
			return SystemInfo.getCpuTemperature();
		} catch (Exception e) {}
		return 0.0;
	}
}
