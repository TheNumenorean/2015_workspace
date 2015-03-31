package org.usfirst.frc.team2984.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.DigitalInput;

public class SensorMonitor {
	
	private HashMap<Sensors, DigitalInput> sensors;
	
	SensorMonitor(){
		this.sensors = new HashMap<Sensors, DigitalInput>();
		sensors.put(Sensors.FRONT_UPPER_BEAM_SENSOR, new DigitalInput(Sensors.FRONT_UPPER_BEAM_SENSOR.getId()));
		sensors.put(Sensors.FRONT_LOWER_BEAM_SENSOR, new DigitalInput(Sensors.FRONT_LOWER_BEAM_SENSOR.getId()));
		sensors.put(Sensors.MIDDEL_BEAM_SENSOR, new DigitalInput(Sensors.MIDDEL_BEAM_SENSOR.getId()));
		sensors.put(Sensors.BACK_BEAM_SENSOR, new DigitalInput(Sensors.BACK_BEAM_SENSOR.getId()));
	}
	
	public boolean isActive(Sensors sensor){
		DigitalInput input = this.sensors.get(sensor);
		return input.get();
	}
}
