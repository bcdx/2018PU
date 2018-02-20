package org.usfirst.frc.team4856.robot.subsystems;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	public static ADXRS450_Gyro gyroSPI = new ADXRS450_Gyro();
	// if anything else goes wrong just substitute WPI_TalonSRX for TalonSRX and see if it works
	public static TalonSRX left1 = new TalonSRX(RobotMap.LeftFrontMotor);
	public static TalonSRX left2 = new TalonSRX(RobotMap.LeftBackMotor);
	public static TalonSRX right1 = new TalonSRX(RobotMap.RightFrontMotor);
	public static TalonSRX right2 = new TalonSRX(RobotMap.RightBackMotor);

    // Put methods for controlling this subsystem here. Call these from Commands.
	
	// ENCODER DISTANCE
	
//	public double getLeftEncoderDistance() {
//		return left2.getSelectedSensorPosition(0) * RobotMap.inchesPerPulse;
//	}
//	
//	public double getRightEncoderDistance() {
//		return right2.getSelectedSensorPosition(0) * RobotMap.inchesPerPulse;
//	}
//	
//	public void resetEncoders() {
//		left2.setSelectedSensorPosition(0, 0, 0);
//		right2.setSelectedSensorPosition(0, 0, 0);
//		System.out.println("encoders reset");
//	}
	
	// MOTOR CONTROLS
	
	public void stop(){
	   	left1.set(ControlMode.PercentOutput, 0);
	    left2.set(ControlMode.PercentOutput, 0);
	    right1.set(ControlMode.PercentOutput, 0);
	    right2.set(ControlMode.PercentOutput, 0);
	}
	
	public void setLeftSpeed(double speed){
	   	left1.set(ControlMode.PercentOutput, speed);
	    left2.set(ControlMode.PercentOutput, speed);
	}
	
	public void setRightSpeed(double speed){
	   	right1.set(ControlMode.PercentOutput, -speed);
	    right2.set(ControlMode.PercentOutput, -speed);
	}
	
    public void arcadeDrive(double throttleValue, double turnValue) {
    	
    	double leftMtr;
    	double rightMtr;
    	
    	if (Math.abs(turnValue) > 0.3) {
	    	leftMtr = throttleValue + 0.75*turnValue;
	    	rightMtr = throttleValue - 0.75*turnValue;
    	} else {
    		leftMtr = throttleValue;
    		rightMtr = throttleValue;
    	}
    	
//    	System.out.println("L123"+turnValue);
    	if (Math.abs(throttleValue) < 0.1 && Math.abs(turnValue) < 0.1) {
    		setLeftSpeed(0);
	        setRightSpeed(0);
    	} else if (Math.abs(throttleValue)> 0.1 || Math.abs(turnValue) > 0.1) {
    		setLeftSpeed(-0.85*rightMtr);
	        setRightSpeed(-leftMtr*0.85);
    	} 
        
       // System.out.println("L132"+turnValue);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

