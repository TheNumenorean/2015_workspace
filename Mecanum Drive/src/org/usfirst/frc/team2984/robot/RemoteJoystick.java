package org.usfirst.frc.team2984.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class RemoteJoystick {

	private NetworkTable table;

	public RemoteJoystick(String name, boolean server) {

		if (server) {
			NetworkTable.setIPAddress("10.29.84.2");
			table = NetworkTable.getTable(name);
		} else {
			table = NetworkTable.getTable(name);
		}

	}


	public String getString(String key){
		if(this.isConnected()){
			if(table.containsKey(key)){
				return table.getString(key);
			}
		}
		return "";
	}
	
	//@TODO: implement whatever this does
	public int getInteger(String name){
		if (this.isConnected()){
			return (int) table.getNumber(name);
		}
		return 0;
	}
	
	public double getX() {
		if (this.isConnected()){
			return table.getNumber("axis2");
		}
		return 0;
	}

	public double getY() {
		if (this.isConnected()){
			return table.getNumber("axis1");
		}
		return 0;
	}

	public double getTwist() {
		if (this.isConnected()){
			return table.getNumber("yaw");
		}
		return 0;
	}

	public boolean getButton(int num) {
		if (this.isConnected()){
			return table.getBoolean("button" + num);
		}
		return false;
	}

	public double getAxis(int num) {
		if (this.isConnected()){
			return table.getNumber("axis" + num);
		}
		return 0;
	}

	public double getNumber(String name) {
		if (this.isConnected()){
			return table.getNumber(name);
		}
		return 0;
	}

	public double getMode() {
		if (this.isConnected()){
			return table.getNumber("grabber");
		}
		return 0;
	}

	public boolean isConnected() {
		return table.isConnected();
	}

	public void setButton(int num, boolean value) {
		table.putBoolean("button", value);
	}

	public long getTimeTaken() {
		long time = (long) table.getNumber("TimeStarted");
		table.putNumber("TimeStartedServer", System.currentTimeMillis());
		return System.currentTimeMillis() - time;
	}

	public void setAxis(int num, double value) {
		table.putNumber("axis" + num, value);
	}

	public void setNumber(String name, double value) {
		table.putNumber(name, value);
	}
	
	public void setBoolean(String name, boolean value){
		table.putBoolean(name, value);
	}

}