package org.usfirst.frc.team4856.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public interface RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
	
	public static final int LeftFrontMotor = 1;
	public static final int LeftBackMotor = 2;
	public static final int RightFrontMotor = 3;
	public static final int RightBackMotor = 4;
	
	// ENCODER CONSTANTS
	
	public static final double wheelDiameter = 6; 
	public static final int pulsesPerRevolution = 4096;
	public static final double inchesPerRevolution = Math.PI * wheelDiameter;
	public static final double inchesPerPulse = inchesPerRevolution / pulsesPerRevolution;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
