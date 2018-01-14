package org.usfirst.frc.team4856.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.subsystems.*;


/**
 *
 */
public class PlaceGear extends Command {

    public PlaceGear() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.scoop);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    //	Robot.scoop.initializeCounter();
    	Robot.scoop.place();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return (boolean) (Robot.scoop.di_place.get());
	}
	// Make this return true when this Command no longer needs to run execute()

    // Called once after isFinished returns true
    protected void end() {
    	Robot.scoop.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    	end();

    }

//	@Override

}
