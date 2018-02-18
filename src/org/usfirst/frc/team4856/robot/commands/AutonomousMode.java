package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.RobotMap;
import org.usfirst.frc.team4856.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

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
	
	public void driveDistance(double targetDistance, double speed){
		resetEncoders();
//		while (getRightEncoderDistance() > 1){
//			System.out.println("reset");
//		}
		System.out.println("current distance: " + getRightEncoderDistance());
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
		}
		stop();
	}
	
	
	public void turnRight(double angle, double speed){
		double initialAngle = gyroSPI.getAngle();
		Timer.delay(0.01);
		double currentSpeed = speed;
		
		while (gyroSPI.getAngle() < initialAngle + angle) {
			Timer.delay(0.01);
			
			setLeftSpeed(currentSpeed);
			setRightSpeed(-currentSpeed);
			
			if(gyroSPI.getAngle() > initialAngle + angle - 20 && currentSpeed > 0.3) {
				currentSpeed = currentSpeed * 0.8;
			}
			
		}
		
		System.out.println("DONE TURNING RIGHT");
		
		stop();
	}
	
	public void turnLeft(double angle, double speed){
		double initialAngle = gyroSPI.getAngle();
		Timer.delay(0.01);
		setLeftSpeed(-speed);
		setRightSpeed(speed);
		while (gyroSPI.getAngle() > initialAngle - angle){
			Timer.delay(0.001);
		//	System.out.println("angle:" + gyroSPI.getAngle());  
		//  System.out.println("gyro adjustment:" + gyro_adj);
		}
		stop();
	}

    public AutonomousMode() {
    	timer = new Timer();
        // Use requires() here to declare subsystem dependencies; eg. requires(chassis);
    }
    
		

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	System.out.println("initializing");
    	
    	timer.reset();
    	timer.start();
    	gyroSPI.reset();
    	resetEncoders();
    	Timer.delay(0.05); //0.01 before
    	System.out.println("initial encoder position: " + getRightEncoderDistance());
    	
//    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
//    	if (gameData.length() > 0) {
//    		if (gameData.charAt(0) == 'L') {
//    			//FROM LEFT
//    			driveDistance(120, 0.2);
//    			turnRight(90, 0.15);
//    			//FROM CENTER
//    			driveDistance(50, 0.2);
//    			turnLeft(90, 0.15);s
//    			driveDistance(120, 0.2);
//    			turnRight(90, 0.15);
//    			driveDistance(80, 0.2);
//    			turnRight(90, 0.15);
//    			//FROM RIGHT
//    			driveDistance(120, 0.2);
//    			turnLeft(90, 0.15); 
//    		} else if (gameData.charAt(0) == 'R') {
//    			//FROM LEFT
//    			//FROM CENTER
//    			//FROM RIGHT
//    		}
// 	}
//    	
    	
/*
    	
    	driveDistance(120, 0.2);
    	System.out.println(timer.get());
    	Timer.delay(0.1); 
    	System.out.println("initial encoder position: " + getRightEncoderDistance());
	    
    	driveDistance(120, 0.5);
    	turnRight(90, 0.2);
    	driveDistance(60, 0.4);
    	
 */  	
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("is finished");
    	return timer.get() > 5; //stops autonomous mode when timer is longer than 300 seconds
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("end");
    	stop();
    }

    // Called when another command which requires one or more of the same subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	System.out.println("interrupted");
    }
}