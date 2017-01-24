package PiRoom;

import java.util.Timer;
import java.util.TimerTask;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class MotionSensor {
	private GpioPinDigitalInput sensor;
	private int counter=0;
	
	public MotionSensor(Pin pin) {
		sensor=GpioFactory.getInstance().provisionDigitalInputPin(pin);
	}
	
	public void startMonitor() {
		sensor.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent arg0) {
				if (arg0.getState()==PinState.HIGH) {
					counter++;
					Timer t=new Timer();
					t.schedule(new TimerTask() {
						@Override
						public void run() {
							counter--;
						}
					}, 60000);
				}
			}
		});
	}
	
	public void stopMonitor() {
		sensor.removeAllListeners();
	}
	
	public double getCount() {
		return this.counter/60.0;
	}
	
}
