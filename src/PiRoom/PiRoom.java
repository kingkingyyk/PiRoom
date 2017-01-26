package PiRoom;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.UIManager;

import com.pi4j.gpio.extension.mcp.MCP3424GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP3424Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;

public class PiRoom {

	public static final GpioController GPIO = GpioFactory.getInstance();
	
	public static final MotionSensor MOTION_SENSOR=new MotionSensor(RaspiPin.GPIO_01);
	public static final int ADC_I2C_ADDRESS=0x68;
	public static MCP3424GpioProvider ADC;
	public static GpioPinAnalogInput SENSOR_LIGHT;
	public static GpioPinAnalogInput SENSOR_TEMPERATURE;
	
	public static double PiTemperatureReading=0.0;
	public static int PiClockSpeedReading=0;
	public static double PiMemoryUsageReading=0.0;
	public static double MotionReading=0.0;
	public static double LightReading=0.0;
	public static double TemperatureReading=0.0;
	
	public static final GpioPinDigitalOutput RELAY_FAN=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_00);
	public static final GpioPinDigitalOutput RELAY_LIGHT=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_02);
	
	public static boolean AutomationOn=false;
	public static Timer AutoTaskTimer=new Timer();
	public static AutomationTask AutoTask=null;
	
	public static double FanAutomationMotionThreshold=1.0;
	public static double FanAutomationTemperatureThreshold=25.0;
	
	public static double LightAutomationMotionThreshold=1.0;
	public static double LightAutomationLightThreshold=50.0;
	
	public static MainUI ui;

	public static void initializeADC() throws Exception {
		System.out.println("===========Starts ADC Init===========");
		ADC=new MCP3424GpioProvider(I2CBus.BUS_1, ADC_I2C_ADDRESS, 18, 1);
		SENSOR_LIGHT=GPIO.provisionAnalogInputPin(ADC,MCP3424Pin.GPIO_CH0);
		SENSOR_TEMPERATURE=GPIO.provisionAnalogInputPin(ADC,MCP3424Pin.GPIO_CH1);
		System.out.println("===========Ends ADC Init===========");
	}
	
	public static void main (String [] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		initializeADC();
		
		ui=new MainUI();
		ui.setLocationRelativeTo(null);
		ui.setVisible(true);
		ui.updateControlType(false);
		
		MOTION_SENSOR.startMonitor();
		Timer t=new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				PiTemperatureReading=Utility.getPiTemperature();
				PiClockSpeedReading=Utility.getPiClockSpeed();
				PiMemoryUsageReading=Utility.getPiMemoryUsage();
				MotionReading=MOTION_SENSOR.getCount();
				LightReading=(ADC.getAnalogValue(SENSOR_LIGHT)/2)*2.048*100; // voltage divider -> 3K + 2K, so we will get max 2V...
				TemperatureReading=(2.0/ADC.getAnalogValue(SENSOR_TEMPERATURE)-1); //ported from : http://wiki.seeed.cc/Grove-Temperature_Sensor_V1.2/
				TemperatureReading=1.0/(Math.log(TemperatureReading)/4275+1/298.15)-273.15;
				ui.setPiTemperatureReading(PiTemperatureReading);
				ui.setPiClockSpeedReading(PiClockSpeedReading);
				ui.setPiMemoryUsageReading(PiMemoryUsageReading);
				ui.setMotionReading(MotionReading);
				ui.setLightReading(LightReading);
				ui.setTemperatureReading(TemperatureReading);
			}
			
		},0,1000);
		
		setFanStatus(false,true,true);
		setLightStatus(false,true,true);
		
		setFanAutomationMotionThreshold(FanAutomationMotionThreshold,true);
		setFanAutomationTemperatureThreshold(FanAutomationTemperatureThreshold,true);
		
		setLightAutomationMotionThreshold(LightAutomationMotionThreshold,true);
		setLightAutomationLightThreshold(LightAutomationMotionThreshold,true);
		
		startConnection();
	}

	public static void setAutomationStatus (boolean on) {
		AutomationOn=on;
		if (AutoTask!=null) {
			AutoTask.cancel();
			AutoTask=null;
		}
		if (on) {
			AutoTask=new AutomationTask();
			AutoTaskTimer.schedule(AutoTask,0,1000);
		}
		ui.updateControlType(on);
	}
	
	public static void setFanStatus (boolean on, boolean updateUI, boolean recurse) {
		if (on) RELAY_FAN.setState(PinState.LOW);
		else RELAY_FAN.setState(PinState.HIGH);
		
		if (updateUI) ui.updateFanButton(on,recurse);
	}
	
	public static void setLightStatus (boolean on, boolean updateUI, boolean recurse) {
		if (on) RELAY_LIGHT.setState(PinState.LOW);
		else RELAY_LIGHT.setState(PinState.HIGH);
		
		if (updateUI) ui.updateLightButton(on,recurse);
	}
	
	public static void setFanAutomationMotionThreshold (double d, boolean recurse) {
		FanAutomationMotionThreshold=d;
		if (ui!=null) ui.setFanAutomationMotionValue(d,recurse);
	}
	
	public static void setFanAutomationTemperatureThreshold (double d, boolean recurse) {
		FanAutomationTemperatureThreshold=d;
		if (ui!=null) ui.setFanAutomationTemperatureValue(d,recurse);
	}
	
	public static void setLightAutomationMotionThreshold (double d, boolean recurse) {
		LightAutomationMotionThreshold=d;
		if (ui!=null) ui.setLightAutomationMotionValue(d,recurse);
	}
	
	public static void setLightAutomationLightThreshold (double d, boolean recurse) {
		LightAutomationLightThreshold=d;
		if (ui!=null) ui.setLightAutomationLightValue(d,recurse);
	}
	
	public static void startRecordReading() {
		
	}
	
	public static void startConnection() throws Exception {
		System.out.println("===========Starts Listening on Port 14521===========");
		@SuppressWarnings("resource")
		ServerSocket ss=new ServerSocket(14521);
		while (true) {
			Socket sc=ss.accept();
			ObjectInputStream ois=new ObjectInputStream(sc.getInputStream());
			ObjectOutputStream oos=new ObjectOutputStream(sc.getOutputStream());
			Object o=ois.readObject();
			if (o instanceof ArrayList) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				ArrayList<Object> list=(ArrayList)o;
				oos.writeObject(processSocket((String)list.get(0),list.get(1)));
			}
			sc.close();
		}
	}
	
	public static Object processSocket (String opcode, Object operand) {
		Object toReturn=null;
		switch (opcode) {
			case "GetPeriodicReadings" : {
				ArrayList<Object> data=new ArrayList<Object>();
				data.add(AutomationOn ? 1 : 0);
				data.add(AutoTask==null ? 0 : AutoTask.getCountdown());
				data.add(RELAY_FAN.isLow() ? 1 : 0);
				data.add(RELAY_LIGHT.isLow() ? 1 : 0);
				data.add(PiTemperatureReading);
				data.add(PiClockSpeedReading);
				data.add(PiMemoryUsageReading);
				data.add(MotionReading);
				data.add(LightReading);
				data.add(TemperatureReading);
				toReturn=data;
				break;
			}
			case "GetPiTemperatureReading" : {
				toReturn=PiTemperatureReading;
				break;
			}
			case "GetPiClockSpeedReading" : {
				toReturn=PiClockSpeedReading;
				break;
			}
			case "GetPiMemoryUsageReading" : {
				toReturn=PiMemoryUsageReading;
				break;
			}
			case "GetMotionReading" : {
				toReturn=MotionReading;
				break;
			}
			case "GetLightReading" : {
				toReturn=LightReading;
				break;
			}
			case "GetTemperatureReading" : {
				toReturn=TemperatureReading;
				break;
			}
			case "SetAutomationStatus" : {
				setAutomationStatus((boolean)operand);
				break;
			}
			case "GetAutomationStatus" : {
				toReturn=AutomationOn;
				break;
			}
			case "SetFanStatus" : {
				setFanStatus((boolean) operand,true,true);
				break;
			}
			case "GetFanStatus" : {
				toReturn=RELAY_FAN.isLow();
				break;
			}
			case "SetLightStatus" : {
				setLightStatus((boolean) operand,true,true);
				break;
			}
			case "GetLightStatus" : {
				toReturn=RELAY_LIGHT.isLow();
				break;
			}
			case "SetFanControlMotionThreshold" : {
				setFanAutomationMotionThreshold((double) operand,true);
				break;
			}
			case "GetFanControlMotionThreshold" : {
				toReturn=FanAutomationMotionThreshold;
				break;
			}
			case "SetFanControlTemperatureThreshold" : {
				setFanAutomationTemperatureThreshold((double) operand,true);
				break;
			}
			case "GetFanControlTemperatureThreshold" : {
				toReturn=FanAutomationTemperatureThreshold;
				break;
			}
			
			case "SetLightControlMotionThreshold" : {
				setLightAutomationMotionThreshold((double) operand,true);
				break;
			}
			case "GetLightControlMotionThreshold" : {
				toReturn=LightAutomationMotionThreshold;
				break;
			}
			case "SetLightControlLightThreshold" : {
				setLightAutomationLightThreshold((double) operand,true);
				break;
			}
			case "GetLightControlLightThreshold" : {
				toReturn=LightAutomationLightThreshold;
				break;
			}
		}
		return toReturn;
	}
}
