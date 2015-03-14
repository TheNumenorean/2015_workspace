package org.usfirst.frc.team2984.robot;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive class.
 */
public class Robot extends IterativeRobot {
	
    private MecanumDrive mecanumDrive;
    private ToteLifter totelifter;
     private Joystick stick;
    private RemoteJoystick stick2;
    private double lastValue = -1;
    private boolean gabby = true;
    
    // The channel on the driver station that the joystick is connected to
    final int joystickChannel	= 0;
	private boolean moveingToPosition;

    public void robotInit() {
        stick = new Joystick(joystickChannel);
        stick2 = new RemoteJoystick("CustomData1", false);
        mecanumDrive = new MecanumDrive(14,4,16,1);
        this.totelifter = new ToteLifter(12,9);
    }

    /**
     * Runs the motors with Mecanum drive.
     */
    public void teleopPeriodic() {
    	if(stick2.getMode()>0){
    		this.gabby = true;
    	}
    	if(stick2.getMode()< 0){
    		this.gabby = false;
    	}
    	if(this.gabby){
    		this.mecanumDrive.drive(0, 0, 0, 0);
    	} else {
    		this.mecanumDrive.drive(0, 0, 0, 0);	
    	}
    	if(!stick.getRawButton(4)){
	    	int movement = stick.getRawButton(3) ? 3000 : 0;
	    	movement = stick.getRawButton(1) ? 6480 : movement;
	    	this.moveingToPosition = stick.getRawButton(2);
	    	if(Math.abs(stick.getY())>0.1){
				this.totelifter.set(-stick.getY());
			} else if(!this.moveingToPosition) {
				this.totelifter.set(0.0);
			} else{
				this.totelifter.set(movement);
			}
    	} else {
			this.totelifter.reset();
    	}
			
    	this.totelifter.debug();
		SmartDashboard.putString("Value That it got", this.stick2.getString("Encoder Locations"));

    	if(this.lastValue == -1){
    		this.lastValue = this.stick2.getTimeTaken();
    	}
    }
    
}
