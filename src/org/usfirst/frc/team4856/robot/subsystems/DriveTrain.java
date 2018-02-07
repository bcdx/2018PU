package org.usfirst.frc.team4856.robot.subsystems;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	// if anything else goes wrong just substitute WPI_TalonSRX for TalonSRX and see if it works
	public static TalonSRX left1 = new TalonSRX(RobotMap.LeftFrontMotor);
	public static TalonSRX left2 = new TalonSRX(RobotMap.LeftBackMotor);
//	public static SpeedControllerGroup left = new SpeedControllerGroup(left1, left2);
	public static TalonSRX right1 = new TalonSRX(RobotMap.RightFrontMotor);
	public static TalonSRX right2 = new TalonSRX(RobotMap.RightBackMotor);
//	public static SpeedControllerGroup right = new SpeedControllerGroup(right1, right2);

    // Put methods for controlling this subsystem here. Call these from Commands.
	
	// ENCODER DISTANCE
	
	public double getLeftEncoderDistance() {
		return Robot.drivetrain.left2.getSelectedSensorPosition(0) * RobotMap.inchesPerPulse;
	}
	
//	public void resetEncoders() {
//		Robot.drivetrain.left2.setSelectedSensorPosition(0, 0, 0);
//		Robot.drivetrain.right2.setSelectedSensorPosition(0, 0, 0);
//	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

