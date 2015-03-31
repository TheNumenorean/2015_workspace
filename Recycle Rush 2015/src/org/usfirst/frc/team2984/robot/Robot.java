package org.usfirst.frc.team2984.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private static final int RATIO = 50;
	private static final double MAX_POWER = 0.5;
	private RobotDrive myRobot;
	private Joystick stick;
	private BuiltInAccelerometer accelerometer;
	private Talon motor0;
	private Talon motor1;
	private Talon motor2;
	private Talon motor3;
	private CANTalon canMotor1;
	private CANTalon canMotor2;
	private CANTalon canMotor3;
	private CANTalon canMotorX;
	private CANTalon backLeftWheel;
	private CANTalon backRightWheel;
	private CANTalon frontRightWheel;
	private CANTalon frontLeftWheel;
	private DigitalInput lightSensor;
	private int autoLoopCounter;
	private CameraServer camServer;
	private double power = 0;
	private boolean unSafe = false;
	private boolean unSafeTogglePressed = false;
	private AnalogInput exampleAnalog;
	private double[] last30 = new double[30];
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// myRobot = new RobotDrive(1,2);
//		motor0 = new Talon(0);
//		motor1 = new Talon(1);
//		motor2 = new Talon(2);
//		motor3 = new Talon(3);
//		accelerometer = new BuiltInAccelerometer();
//		canMotor1 = new CANTalon(1);
//		canMotor2 = new CANTalon(2);
//		canMotor3 = new CANTalon(3);
		backLeftWheel = new CANTalon(5);
		backRightWheel = new CANTalon(1);
		frontRightWheel = new CANTalon(4);
		frontLeftWheel = new CANTalon(3);
//		lightSensor = new DigitalInput(10);
		stick = new Joystick(0);
//		exampleAnalog = new AnalogInput(1);
//		camServer = CameraServer.getInstance();
//		for (int i = 0; i < 30; i++) {
//			last30[i] = 0;
//		}
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	public void autonomousInit() {
		autoLoopCounter = 0;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		// if(autoLoopCounter < 10000) //Check if we've completed 100 loops
		// (approximately 2 seconds)
		// {
		// motor0.set(1.0); // drive forwards half speed
		// autoLoopCounter++;
		// } else {
		// motor0.set(0.0); // stop robot
		// }
//		double runningAverage = getRunningAverage();

		// controles the motor based on the potentiomitor

//		if (accelerometer.getX() > 0.05) {
//			canMotor2.set(0.55);
//		} else if (accelerometer.getX() < -0.05) {
//			canMotor2.set(-0.55);
//		} else {
//			canMotor2.set(0.0);
//		}
//		canMotor1.set((600-runningAverage)/600);
//		canMotor2.set((600-runningAverage)/600);
//		canMotor3.set((600-runningAverage)/600);
		
//		System.out.println("Running Average: " + runningAverage);
		canMotor2.setVoltageRampRate(1.5);
		if(!lightSensor.get()){
			canMotor2.set(1.0);
		} else {
			canMotor2.set(0);
		}
	}

	private double getRunningAverage() {
		double raw = this.exampleAnalog.getValue()/2;
		double runningAverage = raw;
		for (int i = 0; i < 29; i++) {
			runningAverage += last30[i];
			last30[i] = last30[i + 1];
		}
		last30[29] = raw;
		runningAverage = runningAverage / 30;
		return runningAverage;
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	public void teleopInit() {
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		// double currentPower = stick.getMagnitude();
		// power = (power * RATIO + currentPower)/(RATIO + 1);
		// power = (power < MAX_POWER) ? power : MAX_POWER;
		 if(stick.getRawButton(3) && !this.unSafeTogglePressed){
			 this.unSafeTogglePressed = true;
			 this.unSafe = !this.unSafe;
		 } else if(!stick.getRawButton(3)) {
			 this.unSafeTogglePressed = false;
		 }
		 
		 if(unSafe){
			 backLeftWheel.setVoltageRampRate(-1);
				backRightWheel.setVoltageRampRate(-1);
				frontRightWheel.setVoltageRampRate(-1);
				frontLeftWheel.setVoltageRampRate(-1);
		 } else {
			 backLeftWheel.setVoltageRampRate(1.5);
				backRightWheel.setVoltageRampRate(1.5);
				frontRightWheel.setVoltageRampRate(1.5);
				frontLeftWheel.setVoltageRampRate(1.5);
		 }

//		if (stick.getX() > 0.1) {
//			canMotor1.set(1.0);
//		} else if (stick.getX() < -0.1) {
//			canMotor1.set(-1.0);
//		} else {
//			canMotor1.set(0.0);
//		}
//		
		if (stick.getX() > 0.1) {
			backLeftWheel.set(stick.getX());
			backRightWheel.set(stick.getX());
			frontRightWheel.set(stick.getX());
			frontLeftWheel.set(stick.getX());
		} else if (stick.getX() < -0.1) {
			backLeftWheel.set(stick.getX());
			backRightWheel.set(stick.getX());
			frontRightWheel.set(stick.getX());
			frontLeftWheel.set(stick.getX());
		} else {
			backLeftWheel.set(0.0);
			backRightWheel.set(0.0);
			frontRightWheel.set(0.0);
			frontLeftWheel.set(0.0);;
		}
//		System.out.println(accelerometer.getX());

		// myRobot.arcadeDrive(stick);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

}
