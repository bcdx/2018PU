package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnLeft extends Command {
	
	Timer timer;
	double speed;
	double initialAngle;
	double angle;

    public TurnLeft(double angle, double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.angle = angle;
		this.speed = speed;
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
    	initialAngle = Robot.drivetrain.gyroSPI.getAngle();
    	System.out.println("starting turn left");
    	System.out.println(initialAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		setLeftSpeed(-speed);
		setRightSpeed(speed);
		if(Robot.drivetrain.gyroSPI.getAngle() < initialAngle - angle + 20 && speed > 0.3) {
			speed = speed * 0.8;
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.drivetrain.gyroSPI.getAngle() < initialAngle - angle) || !DriverStation.getInstance().isAutonomous();
    }

    // Called once after isFinished returns true
    protected void end() {
    	stop();
    	System.out.println(Robot.drivetrain.gyroSPI.getAngle());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
