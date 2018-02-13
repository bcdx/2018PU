package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.RobotMap;
import org.usfirst.frc.team4856.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.PIDController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousMode extends CommandGroup {
	public ADXRS450_Gyro gyroSPI = new ADXRS450_Gyro();
	Timer timer;
	double gyro_adj = 0.0;
	double temp = 0.5;
	double k1 = 0.002;
	int epsilon = 2;	
	
	public void resetEncoders() {
		Robot.drivetrain.left2.setSelectedSensorPosition(0, 0, 0);
		Robot.drivetrain.right2.setSelectedSensorPosition(0, 0, 0);
	}
	
	public double getLeftEncoderDistance() {
		return DriveTrain.left2.getSelectedSensorPosition(0) * RobotMap.inchesPerPulse;
	}
	
	public double getRightEncoderDistance() {
		return DriveTrain.right2.getSelectedSensorPosition(0) * RobotMap.inchesPerPulse;
	}
	
	public void driveDistance(double targetDistance, double speed){
		resetEncoders();
		while (getRightEncoderDistance() > 1){
			System.out.println("reset");
		}
	//	System.out.println("current distance: " + getRightEncoderDistance());
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, speed);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, speed);
	    Robot.drivetrain.right1.set(ControlMode.PercentOutput, -speed);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, -speed);
		while (getRightEncoderDistance() < targetDistance){
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
			
			Robot.drivetrain.left1.set(ControlMode.PercentOutput, speed+gyro_adj);
		    Robot.drivetrain.left2.set(ControlMode.PercentOutput, speed+gyro_adj);
			//System.out.println("angle: " + gyroSPI.getAngle());  
		   //System.out.println("gyro adjustment: " + gyro_adj);
		    //System.out.println("distance in inches: "+ getRightEncoderDistance());
		}
		stop();
	}
	
	public void turnRight(double angle, double speed){
		double initialAngle = gyroSPI.getAngle();
		Timer.delay(0.01);
		Robot.drivetrain.left1.set(ControlMode.PercentOutput, speed);
		Robot.drivetrain.left2.set(ControlMode.PercentOutput, speed);
		Robot.drivetrain.right1.set(ControlMode.PercentOutput, speed);
		Robot.drivetrain.right2.set(ControlMode.PercentOutput, speed);
		while (gyroSPI.getAngle() < initialAngle + angle){ 
			Timer.delay(0.001);
		//	System.out.println("angle:" + gyroSPI.getAngle());  
		//  System.out.println("gyro adjustment:" + gyro_adj);
		}
		stop();
	}
	
	public void turnLeft(double angle, double speed){
		gyroSPI.reset();
		while (gyroSPI.getAngle() > -angle){
			Robot.drivetrain.left1.set(ControlMode.PercentOutput, -speed);
			Robot.drivetrain.left2.set(ControlMode.PercentOutput, -speed);
			Robot.drivetrain.right1.set(ControlMode.PercentOutput, -speed);
			Robot.drivetrain.right2.set(ControlMode.PercentOutput, -speed);
			Timer.delay(0.02);
		//	System.out.println("angle:" + gyroSPI.getAngle());  
		//   System.out.println("gyro adjustment:" + gyro_adj);
		}
		stop();
	}
	
	public void stop(){
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);
	}

    public AutonomousMode() {
    	timer = new Timer();
        // Use requires() here to declare subsystem dependencies; eg. requires(chassis);
    }
    
		

    // Called just before this Command runs the first time
    protected void initialize() {
   	timer.reset();
    	timer.start();
    	gyroSPI.reset();
    	resetEncoders();
    	Timer.delay(0.1); 
    	System.out.println("initial encoder position: " + getRightEncoderDistance());
	    
    	driveDistance(182.5, 0.5);
    	Timer.delay(5);
    	turnRight(90, 0.1);
    	Timer.delay(5);
    	driveDistance(60, 0.4);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timer.get() > 20; //stops autonomous mode when timer is longer than 300 seconds
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("end");
    	stop();
    }

    // Called when another command which requires one or more of the same subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}