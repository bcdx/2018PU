package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.OI;
import org.usfirst.frc.team4856.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ScaleUp extends Command{

	private Joystick thirdStick;

    public ScaleUp() {
        // Use requires() here to decqare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.scaler);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("extend command called");
    	Robot.scaler.up();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.scaler.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
}
	
}
