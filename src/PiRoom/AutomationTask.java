package PiRoom;

import java.util.TimerTask;

public class AutomationTask extends TimerTask {
	private int countdown=60;
	
	public int getCountdown() {
		return this.countdown;
	}
	
	@Override
	public void run() {
		PiRoom.ui.updateCountdownTimer(countdown--);
		
		if (countdown==0) {
			if (PiRoom.RELAY_FAN.isHigh()) {
				if (PiRoom.MotionReading>=PiRoom.FanAutomationMotionThreshold && PiRoom.TemperatureReading>=PiRoom.FanAutomationTemperatureThreshold)
					PiRoom.setFanStatus(true,true,true);
			} else {
				if (PiRoom.MotionReading<PiRoom.FanAutomationMotionThreshold && PiRoom.TemperatureReading<PiRoom.FanAutomationTemperatureThreshold)
					PiRoom.setFanStatus(false,true,true);
			}
			
			if (PiRoom.RELAY_LIGHT.isHigh()) {
				if (PiRoom.MotionReading>=PiRoom.LightAutomationMotionThreshold && PiRoom.LightReading<=PiRoom.LightAutomationLightThreshold)
					PiRoom.setLightStatus(true,true,true);
			} else {
				if (PiRoom.MotionReading<PiRoom.LightAutomationMotionThreshold && PiRoom.LightReading>PiRoom.LightAutomationLightThreshold)
					PiRoom.setLightStatus(false,true,true);
			}
			countdown=60;
		}
	}

}
