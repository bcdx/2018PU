package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenServo extends Command {
	
    public OpenServo() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.bucket);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.bucket.open();
    	System.out.println("open servo command called");
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(Robot.bucket.servo.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
      return Robot.bucket.servo.get() > 0.3445;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
