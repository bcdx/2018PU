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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class AutonomousMode extends CommandGroup {
	public ADXRS450_Gyro gyroSPI = new ADXRS450_Gyro();
	Timer timer;
	double gyro_adj = 0.0;
	double temp =.4;
	double k1 = .002;
	int epsilon = 2;




    public AutonomousMode() {
    	timer = new Timer();
    	
        // Use requires() here to declare subsystem dependencies; eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	
    	gyroSPI.reset();
    	
    	
 
    	
    	    	
		//Robot.drivetrain.left1.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
		//Robot.drivetrain.left2.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10); 
		//Robot.drivetrain.right1.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10); 
		//Robot.drivetrain.right2.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
		//Robot.drivetrain.left1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10); 
		//Robot.drivetrain.left2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10); 
		//Robot.drivetrain.right1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10); 
		//Robot.drivetrain.right2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, temp);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, temp);
	    Robot.drivetrain.right1.set(ControlMode.PercentOutput, -temp);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, -temp);
		
	    while (timer.get()<1){ ///outer if loop
    	
			Timer.delay(0.05);
		//		System.out.println(dtf.format(now));
		  
		//   	Timer.delay(2.8); move forward (left and right position)
		//		System.out.println("position: " + Robot.drivetrain.right1.getSelectedSensorPosition(0) + gyroSPI.getAngle()); // getSelectedSensorPosition(int pidIdx) 0 for primary closed-loop
			if (gyroSPI.getAngle()>1) { //inner if/else statements that make the robot drive straight for the first 3 seconds
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
		while (gyroSPI.getAngle()<90){ //outer end of if loop. Hopefully makes robot turn 90 degrees
			Robot.drivetrain.left1.set(ControlMode.PercentOutput,0.15);
			Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0.15);
			Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
			Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);
			Timer.delay(0.5);
			System.out.println("angle:" + gyroSPI.getAngle());  
		    System.out.println("gyro adjustment:" + gyro_adj);
		}
		while (timer.get()>1){ ///outer if loop
			Robot.drivetrain.left1.set(ControlMode.PercentOutput, 0.2);
		    Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0.2);
		    Robot.drivetrain.right1.set(ControlMode.PercentOutput, -0.2);
		    Robot.drivetrain.right2.set(ControlMode.PercentOutput, -0.2);
//			System.out.println("angle:" + gyroSPI.getAngle());  
//		    System.out.println("gyro adjustment:" + gyro_adj);
		}
	    
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);

    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	
//		if (timer.get()<1){ ///outer if loop
//    	
//			Timer.delay(0.05);
//		//		System.out.println(dtf.format(now));
//		  
//		//   	Timer.delay(2.8); move forward (left and right position)
//		//		System.out.println("position: " + Robot.drivetrain.right1.getSelectedSensorPosition(0) + gyroSPI.getAngle()); // getSelectedSensorPosition(int pidIdx) 0 for primary closed-loop
//			if (gyroSPI.getAngle()>1) { //inner if/else statements that make the robot drive straight for the first 3 seconds
//				gyro_adj+=-k1;
//			}
//			else if (gyroSPI.getAngle()<-1){
//				gyro_adj+=k1;
//			}
//			else{
//				gyro_adj=0;
//			}
//			
//			Robot.drivetrain.left1.set(ControlMode.PercentOutput, temp+gyro_adj);
//		    Robot.drivetrain.left2.set(ControlMode.PercentOutput, temp+gyro_adj);
//			System.out.println("angle:" + gyroSPI.getAngle());  
//		    System.out.println("gyro adjustment:" + gyro_adj);
//		}
//		else if (gyroSPI.getAngle()<90){ //outer end of if loop. Hopefully makes robot turn 90 degrees
//			Robot.drivetrain.left1.set(ControlMode.PercentOutput,0.15);
//			Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0.15);
//			Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
//			Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);
//			Timer.delay(0.5);
//			System.out.println("angle:" + gyroSPI.getAngle());  
//		    System.out.println("gyro adjustment:" + gyro_adj);
//		}
//		else {
//			Robot.drivetrain.left1.set(ControlMode.PercentOutput,0);
//			Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0);
//			Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
//			Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);	
//			System.out.println("angle:" + gyroSPI.getAngle());  
//		    System.out.println("gyro adjustment:" + gyro_adj);
//		}
//		if (gyroSPI.getAngle()< (90 + epsilon) &(gyroSPI.getAngle()>(90 - epsilon)){
//			Robot.drivetrain.left1.set(ControlMode.PercentOutput,0.1);
//			Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0.1);
//			Robot.drivetrain.right1.set(ControlMode.PercentOutput, -0.01);
//			Robot.drivetrain.right2.set(ControlMode.PercentOutput, -0.01);
		
    	//}
//			
//			
		}
    
           
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timer.get() > 300; //stops autonomous mode when timer is longer than 300 seconds
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("end end");
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