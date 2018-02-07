package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;

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
	double temp = 0.4;
	double k1 = 0.002;
	int epsilon = 2;
	
	public void resetEncoders() {
		Robot.drivetrain.left2.setSelectedSensorPosition(0, 0, 0);
		Robot.drivetrain.right2.setSelectedSensorPosition(0, 0, 0);
	}

	public void driveStraight(double time){
		double currentTime = timer.get();
		
		while (timer.get() < currentTime + time){ 
		    	
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
				
				Robot.drivetrain.left1.set(ControlMode.PercentOutput, temp+gyro_adj);
			    Robot.drivetrain.left2.set(ControlMode.PercentOutput, temp+gyro_adj);
				System.out.println("angle:" + gyroSPI.getAngle());  
			    System.out.println("gyro adjustment:" + gyro_adj);
			}
		
	}
	
	public void turn(double angle){
		while (gyroSPI.getAngle()< angle){ //outer end of if loop. Hopefully makes robot turn 90 degrees
			Robot.drivetrain.left1.set(ControlMode.PercentOutput,0.15);
			Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0.15);
			Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
			Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);
			Timer.delay(0.5);
			System.out.println("angle:" + gyroSPI.getAngle());  
		    System.out.println("gyro adjustment:" + gyro_adj);
		}
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
    	System.out.println("initial encoder position: " + Robot.drivetrain.left2.getSelectedSensorPosition(0));
    	
    	
	   	//Robot.drivetrain.left1.set(ControlMode.PercentOutput, 0.1);
	    //Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0.1);
	    //Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
	    //Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);
	    
	    Timer.delay(10.0);
	    System.out.println("encoder +2: " + Robot.drivetrain.left2.getSelectedSensorPosition(0));
	    
	    //Timer.delay(2.0);
	    //System.out.println("encoder +4: " + Robot.drivetrain.left2.getSelectedSensorPosition(0));
	    
	    //Timer.delay(2.0);
	    //System.out.println("encoder +6: " + Robot.drivetrain.left2.getSelectedSensorPosition(0));
	    
	    //Timer.delay(2.0);
	    //System.out.println("encoder +8: " + Robot.drivetrain.left2.getSelectedSensorPosition(0));
	    
	    //stop();
	    resetEncoders();
	    Timer.delay(2.0);
	    System.out.println("encoder +end: " + Robot.drivetrain.left2.getSelectedSensorPosition(0));
	    
	   // System.out.println("encoder position: " + Robot.drivetrain.left2.getSelectedSensorPosition(0));

//	    driveStraight(5);
//		turn(90);
//	    driveStraight(1);
//	    stop();
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
//    	double prevReading = Robot.drivetrain.left2.getSelectedSensorPosition(0);
//    	Timer.delay(0.5);
//    	double currentReading = Robot.drivetrain.left2.getSelectedSensorPosition(0);
//    	double changeReading = currentReading - prevReading;
//    	System.out.println("position change: " + changeReading);
//    	System.out.println("current position: " + currentReading);
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timer.get() > 20; //stops autonomous mode when timer is longer than 300 seconds
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("end");
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);
    }

    // Called when another command which requires one or more of the same subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}