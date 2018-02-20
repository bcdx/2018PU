package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.subsystems.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousConveyor extends Command {
	
	private double speed;
	private double time;
	private Timer con_timer;

    public AutonomousConveyor(double s, double t) {
        // Use requires() here to declare subsystem dependencies
    	//super("AutonomousConveyor");
    	requires(Robot.conveyor_belt);
    	speed = s;
    	time = t;
    	con_timer = new Timer();
    	
  
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	con_timer.reset();
    	con_timer.start();
		//System.out.println("initialized" + con_timer.get());
		Robot.conveyor_belt.setSpeed(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return con_timer.get() > time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("auton conveyor command end");
    	Robot.conveyor_belt.stopBelt();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
