package org.usfirst.frc.team2984.robot;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.wpi.first.wpilibj.CANTalon;

public class MecanumDriveTest {
	
	CANTalon frontLeft;
	CANTalon frontRight;
	CANTalon backLeft;
	CANTalon backRight;
	MecanumDrive drive;
	
	@Before
	public void before(){
		frontLeft = Mockito.mock(CANTalon.class);
		frontRight = Mockito.mock(CANTalon.class);
		backLeft = Mockito.mock(CANTalon.class);
		backRight = Mockito.mock(CANTalon.class);
		drive = new MecanumDrive(frontLeft,frontRight,backLeft,backRight);
	}
	
	@Test
	public void driveWithFowardSpeedSetToOneMovesFowardAtSpeedOne() {
		drive.drive(0, 1, 0, 0);
		Mockito.verify(frontLeft).set(1.0);
		Mockito.verify(frontRight).set(1.0);
		Mockito.verify(backLeft).set(1.0);
		Mockito.verify(backRight).set(1.0);
	}
	
	@Test
	public void driveWithFowardAndRightWithSpeedSetToOneMovesFowardAndRightAtSpeedOne() {
		drive.drive(1, 1, 0, 0);
		Mockito.verify(frontLeft).set(1.0);
		Mockito.verify(frontRight).set(0.0);
		Mockito.verify(backLeft).set(0.0);
		Mockito.verify(backRight).set(1.0);
	}
	
	@Test
	public void rotateLeftAtFullSpeedRotatesAtFullSpeedLeft() {
		drive.drive(0, 0, 1, 0);
		Mockito.verify(frontLeft).set(1.0);
		Mockito.verify(frontRight).set(1.0);
		Mockito.verify(backLeft).set(-1.0);
		Mockito.verify(backRight).set(-1.0);
	}
	
	@Test
	public void rotateRightAtFullSpeedRotatesAtFullSpeedRight() {
		drive.drive(0, 0, -1, 0);
		Mockito.verify(frontLeft).set(-1.0);
		Mockito.verify(frontRight).set(-1.0);
		Mockito.verify(backLeft).set(1.0);
		Mockito.verify(backRight).set(1.0);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void rotateVectorOneOneByHalfPIRotatesIt(){
		double[] values = new double[]{1,-1};
		assert(drive.rotateVector(1, 1, Math.PI/2)).equals(values);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void rotateVectorOneOneByForthPIRotatesIt(){
		double[] values = new double[]{1,0};
		assert(drive.rotateVector(1, 1, Math.PI/4)).equals(values);
	}
	
	@Test
	public void setCoastModeTrueChangesCoastModeToTrue(){
		drive.setCoastMode(true);
		Mockito.verify(frontLeft).enableBrakeMode(true);
		Mockito.verify(frontRight).enableBrakeMode(true);
		Mockito.verify(backLeft).enableBrakeMode(true);
		Mockito.verify(backRight).enableBrakeMode(true);
	}
	
	@Test
	public void setCoastModeFalseChangesCoastModeToFalse(){
		drive.setCoastMode(false);
		Mockito.verify(frontLeft).enableBrakeMode(false);
		Mockito.verify(frontRight).enableBrakeMode(false);
		Mockito.verify(backLeft).enableBrakeMode(false);
		Mockito.verify(backRight).enableBrakeMode(false);
	}
	
	@Test
	public void setVoltageRampToSixSetsTheVoltageRampRateToSix(){
		drive.setVoltageRamp(6);
		Mockito.verify(frontLeft).setVoltageRampRate(6.0);
		Mockito.verify(frontRight).setVoltageRampRate(6.0);
		Mockito.verify(backLeft).setVoltageRampRate(6.0);
		Mockito.verify(backRight).setVoltageRampRate(6.0);
	}
	
	@Test
	public void caculateVotageRampRateFindsVoltageBasedOnWheelSpeedsOfHalfToFull(){
		drive.setSafeMode(true, 6, 12);
		drive.drive(0.5, 0, 0, 0);
		double rampRate = drive.caculateVotageRampRate(new double[]{1,0,0,1});
		assertEquals(9.0D,rampRate,0);
	}
	
	@Test
	public void setSafeModeToFalseDisablesRamping(){
		drive.setSafeMode(false, 3, 3);
		Mockito.verify(frontLeft).setVoltageRampRate(-1);
		Mockito.verify(frontRight).setVoltageRampRate(-1);
		Mockito.verify(backLeft).setVoltageRampRate(-1);
		Mockito.verify(backRight).setVoltageRampRate(-1);
	}
	
	@Test
	public void setSafeModeToTrueWithRampingUpAndDownAtThreeSetsRampingToThree(){
		drive.setSafeMode(true, 3, 3);
		drive.drive(0, 0, 0, 0);
		Mockito.verify(frontLeft).setVoltageRampRate(3);
		Mockito.verify(frontRight).setVoltageRampRate(3);
		Mockito.verify(backLeft).setVoltageRampRate(3);
		Mockito.verify(backRight).setVoltageRampRate(3);
	}
	
	@Test
	public void setSafeModeToTrueWithRampingUpAndDownAtThirtySetsRampingToThirty(){
		drive.setSafeMode(true, 30, 30);
		drive.drive(0, 0, 0, 0);
		Mockito.verify(frontLeft).setVoltageRampRate(30);
		Mockito.verify(frontRight).setVoltageRampRate(30);
		Mockito.verify(backLeft).setVoltageRampRate(30);
		Mockito.verify(backRight).setVoltageRampRate(30);
	}
	
	@Test
	public void setSafeModeToTrueWithRampingUpSetToThreeAndRampingDownSetToTwelveSetsRampRateToTwelveAfterSlowDown(){
		drive.setSafeMode(true, 3, 12);
		drive.drive(1, 0, 0, 0);
		drive.drive(0, 0, 0, 0);
		Mockito.verify(frontLeft).setVoltageRampRate(12);
		Mockito.verify(frontRight).setVoltageRampRate(12);
		Mockito.verify(backLeft).setVoltageRampRate(12);
		Mockito.verify(backRight).setVoltageRampRate(12);
	}
	
	@Test
	public void safeModeTrueSetsTheRampingToFowardValueWhenTuring(){
		drive.setSafeMode(true, 8, 12);
		drive.drive(0, 0, 0, 0);
		drive.drive(0, 0, 1, 0);
		Mockito.verify(frontLeft).setVoltageRampRate(8);
		Mockito.verify(frontRight).setVoltageRampRate(8);
		Mockito.verify(backLeft).setVoltageRampRate(8);
		Mockito.verify(backRight).setVoltageRampRate(8);
	}
	
	@Test
	public void safeModeTrueWithANegativeOneRampRateUpAndAFourRampRateUpSetsTheRampRateToFourOnSlowdown(){
		drive.setSafeMode(true, -1, 4);
		drive.drive(1, 0, 0, 0);
		drive.drive(0, 0, 0, 0);
		Mockito.verify(frontLeft).setVoltageRampRate(4);
		Mockito.verify(frontRight).setVoltageRampRate(4);
		Mockito.verify(backLeft).setVoltageRampRate(4);
		Mockito.verify(backRight).setVoltageRampRate(4);
	}
	
	@Test
	public void safeModeTrueWithANegativeOneRampRateUpAndAFourRampRateUpSetsTheRampRateToNegativeOneOnAccelerate(){
		drive.setSafeMode(true, -1, 40);
		drive.drive(0, 0, 0, 0);
		drive.drive(1, 0, 0, 0);
		Mockito.verify(frontLeft).setVoltageRampRate(-1);
		Mockito.verify(frontRight).setVoltageRampRate(-1);
		Mockito.verify(backLeft).setVoltageRampRate(-1);
		Mockito.verify(backRight).setVoltageRampRate(-1);
	}

}