package PiRoom;

import java.util.TimerTask;

public class AutomationTask extends TimerTask {
	private int countdown=60;
	@Override
	public void run() {
		PiRoom.ui.updateCountdownTimer(countdown--);
		
		if (countdown==0) {
			if (PiRoom.RELAY_FAN.isHigh()) {
				if (PiRoom.MOTION_SENSOR.getCount()>=PiRoom.FanAutomationMotionThreshold && PiRoom.SENSOR_TEMPERATURE.getValue()>=PiRoom.FanAutomationTemperatureThreshold)
					PiRoom.setFanStatus(true,true,true);
			} else {
				if (PiRoom.MOTION_SENSOR.getCount()<PiRoom.FanAutomationMotionThreshold && PiRoom.SENSOR_TEMPERATURE.getValue()<PiRoom.FanAutomationTemperatureThreshold)
					PiRoom.setFanStatus(false,true,true);
			}
			
			if (PiRoom.RELAY_LIGHT.isHigh()) {
				if (PiRoom.MOTION_SENSOR.getCount()>=PiRoom.LightAutomationMotionThreshold && PiRoom.SENSOR_LIGHT.getValue()>=PiRoom.LightAutomationLightThreshold)
					PiRoom.setLightStatus(true,true,true);
			} else {
				if (PiRoom.MOTION_SENSOR.getCount()<PiRoom.LightAutomationMotionThreshold && PiRoom.SENSOR_LIGHT.getValue()<PiRoom.LightAutomationLightThreshold)
					PiRoom.setLightStatus(false,true,true);
			}
			countdown=60;
		}
	}

}
