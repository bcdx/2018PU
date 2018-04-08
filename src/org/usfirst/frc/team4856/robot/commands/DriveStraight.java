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
	double gyro_adj = 0.0;
	double temp = 0.5;
	double k1 = 0.002;
	int epsilon = 2;
	double targetDistance;
	
    public DriveStraight(double distance, double speed) {
        // Use requires() here to declare subsystem dependencies
    	//super("AutonomousConveyor");
    	this.speed = speed;
    	targetDistance = distance;
    }
	
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

    // Called just before this Command runs the first time
    protected void initialize() {
    	DriveTrain.gyroSPI.reset();
    	resetEncoders();
    	//System.out.println("initial encoder position: " + getRightEncoderDistance());
    	//System.out.println("drivestraight command initialized");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	setLeftSpeed(speed); 
	    setRightSpeed(speed);
		if (DriveTrain.gyroSPI.getAngle()>1) { 
			gyro_adj+=-k1;
		} else if (DriveTrain.gyroSPI.getAngle()<-1){
			gyro_adj+=k1;
		} else {
			gyro_adj=0;
		}
		
		setLeftSpeed(speed+gyro_adj);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (getRightEncoderDistance() > targetDistance) || !DriverStation.getInstance().isAutonomous();
    }

    // Called once after isFinished returns true
    protected void end() {
    	stop();
    	System.out.println(targetDistance + " drivestraight command end");
  
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
