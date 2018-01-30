package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class AutonomousMode extends CommandGroup {

	Timer timer;
	//double speed = 
//	public void GyroSample() {
//		AnalogGyro myGyro = new AnalogGyro(1);
//		double angle = myGyro.getAngle();
//		System.out.println(angle);
//	}
	


    public AutonomousMode() {
    	timer = new Timer();
        // Use requires() here to declare subsystem dependencies; eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	
		//Robot.drivetrain.left1.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
		//Robot.drivetrain.left2.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10); 
		//Robot.drivetrain.right1.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10); 
		//Robot.drivetrain.right2.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
		//Robot.drivetrain.left1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10); 
		//Robot.drivetrain.left2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10); 
		//Robot.drivetrain.right1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10); 
		//Robot.drivetrain.right2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, 0.3);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0.3);
	    Robot.drivetrain.right1.set(ControlMode.PercentOutput, -0.3);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, -0.3);

    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	
		Timer.delay(3.0);
//		System.out.println(dtf.format(now));
		System.out.println("position: " + Robot.drivetrain.right1.getSelectedSensorPosition(0));        // getSelectedSensorPosition(int pidIdx) 0 for primary closed-loop
//   	Timer.delay(2.8); move forward (left and right position)
		
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