package org.usfirst.frc.team2984.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ToteLifter {
	private CANTalon leftLifterMotor;
	private CANTalon rightLifterMotor;
	private boolean isInPositionMode;
	
	private static final int RESET = 6480;
	
	ToteLifter(int leftMotor, int rightMotor){
		this.leftLifterMotor = new CANTalon(leftMotor);
		this.rightLifterMotor = new CANTalon(rightMotor);
	}
	
	//sets the talons to take a position in .set
	private void setPositionMode(){
		if(!this.isInPositionMode){
			this.leftLifterMotor.changeControlMode(ControlMode.Position);
			this.leftLifterMotor.setPID(1, 0.001, 0, 0, 100, 36, 0);
			this.rightLifterMotor.changeControlMode(ControlMode.Position);
			this.rightLifterMotor.setPID(1, 0.001, 0, 0, 100, 36, 0);
			this.isInPositionMode = true;
		}
	}
	
	//Sets the talons to use the input power in .set
	private void setPowerMode(){
		if(this.isInPositionMode){
			this.isInPositionMode = false;
			this.leftLifterMotor.changeControlMode(ControlMode.PercentVbus);
			this.rightLifterMotor.changeControlMode(ControlMode.PercentVbus);
		}
	}
	
	//sets the speed of a talon
	public void set(double power){
		this.setPowerMode();
		this.leftLifterMotor.set(power);
		this.rightLifterMotor.set(power);
	}
	
	//sets the position of a talon
	public void set(int position){
		this.setPositionMode();
		this.leftLifterMotor.set(position);
		this.rightLifterMotor.set(position);
	}
	
	//Resets the motor, return true when reseted
	public boolean reset(){
		boolean reseted = false;
		this.setPowerMode();
		if(!this.leftLifterMotor.isFwdLimitSwitchClosed()) {
			this.leftLifterMotor.changeControlMode(ControlMode.PercentVbus);
			this.leftLifterMotor.set(0.4);
		} else {
			this.leftLifterMotor.changeControlMode(ControlMode.PercentVbus);
			this.leftLifterMotor.set(0);
		}
		if(!this.rightLifterMotor.isFwdLimitSwitchClosed()) {
			this.rightLifterMotor.changeControlMode(ControlMode.PercentVbus);
			this.rightLifterMotor.set(0.4);
		} else {
			this.rightLifterMotor.changeControlMode(ControlMode.PercentVbus);
			this.rightLifterMotor.set(0);
		}
		if(this.leftLifterMotor.isFwdLimitSwitchClosed()){
    		this.leftLifterMotor.setPosition(RESET);
    		reseted = true;
    	}
    	if(this.rightLifterMotor.isFwdLimitSwitchClosed()){
    		this.rightLifterMotor.setPosition(RESET);
    	} else {
    		reseted = false;
    	}
    	return reseted;
	}
	
	public void debug(){
    	SmartDashboard.putNumber("Encoder Position Left", this.leftLifterMotor.getEncPosition());
		SmartDashboard.putNumber("Encoder Position Right", this.rightLifterMotor.getEncPosition());
		SmartDashboard.putBoolean("Right Limit Switch foward", this.rightLifterMotor.isFwdLimitSwitchClosed());
		SmartDashboard.putBoolean("Right Limit Switch back", this.rightLifterMotor.isRevLimitSwitchClosed());
		SmartDashboard.putBoolean("Left Limit Switch foward", this.leftLifterMotor.isFwdLimitSwitchClosed());
		SmartDashboard.putBoolean("Left Limit Switch back", this.leftLifterMotor.isRevLimitSwitchClosed());
	}
}