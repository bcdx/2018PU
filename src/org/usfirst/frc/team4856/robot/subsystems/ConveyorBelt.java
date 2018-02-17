package org.usfirst.frc.team4856.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ConveyorBelt extends Subsystem {
	public SpeedController beltMotor;
	
	public ConveyorBelt () {
		super();                                                                                                                                                                                      
		beltMotor = new Victor(0);
	}
	
	public void setSpeed(double speed) {
		System.out.println(speed);
		beltMotor.set(speed);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

