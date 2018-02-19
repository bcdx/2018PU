package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.RobotMap;
import org.usfirst.frc.team4856.robot.subsystems.*;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {
	Timer timer;
	private double speed;
	private double time;
	public ADXRS450_Gyro gyroSPI = new ADXRS450_Gyro();
	double gyro_adj = 0.0;
	double temp = 0.5;
	double k1 = 0.002;
	int epsilon = 2;
	
	public void resetEncoders() {
		Robot.drivetrain.left2.setSelectedSensorPosition(0, 0, 0);
		Robot.drivetrain.right2.setSelectedSensorPosition(0, 0, 0);
		System.out.println("encoders reset");
		System.out.println("left encoder position: " + getLeftEncoderDistance());
		System.out.println("right encoder position: " + getRightEncoderDistance());
	}
	
	public double getLeftEncoderDistance() {
		return DriveTrain.left2.getSelectedSensorPosition(0) * RobotMap.inchesPerPulse;
	}
	
	public double getRightEncoderDistance() {
		return Robot.drivetrain.right2.getSelectedSensorPosition(0) * RobotMap.inchesPerPulse;
	}
	
	public void stop(){
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);
	}
	
	public void setLeftSpeed(double speed){
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, speed);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, speed);
	}
	
	public void setRightSpeed(double speed){
	   	Robot.drivetrain.right1.set(ControlMode.PercentOutput, -speed);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, -speed);
	}

	public DriveStraight(double targetDistance, double speed) {
		double currentDistance = getRightEncoderDistance();
	   	setLeftSpeed(speed);
	    setRightSpeed(speed);
		while (getRightEncoderDistance() < currentDistance + targetDistance){
			Timer.delay(0.05);
			if (gyroSPI.getAngle()>1) { 
				gyro_adj+=-k1;
			}
			else if (gyroSPI.getAngle()<-1){
				gyro_adj+=k1;
			}
			else{
				gyro_adj=0;
			}
			
			setLeftSpeed(speed+gyro_adj);
		}
		stop();
		
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	timer.reset();
//    	timer.start();
    	gyroSPI.reset();
    	resetEncoders();
    	Timer.delay(0.05); //0.01 before
    	System.out.println("initial encoder position: " + getRightEncoderDistance());
    	System.out.println("drivestraight command initialized");

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("drivestraight command finished");
//    	return timer.get() > 10;
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	stop();
    	System.out.println("drivestraight command end");
   
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
