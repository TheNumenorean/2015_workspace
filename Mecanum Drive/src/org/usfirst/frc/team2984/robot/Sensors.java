package org.usfirst.frc.team2984.robot;

public enum Sensors {
	FRONT_UPPER_BEAM_SENSOR(12), FRONT_LOWER_BEAM_SENSOR(13), MIDDEL_BEAM_SENSOR(11), BACK_BEAM_SENSOR(10);
	private int id;
	
	Sensors(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
}
