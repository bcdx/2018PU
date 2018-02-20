package org.usfirst.frc.team4856.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;
import org.opencv.core.*;
import org.usfirst.frc.team4856.robot.commands.*;
import org.usfirst.frc.team4856.robot.subsystems.*;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The VM (virtual machine) is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static OI oi;
	public static DriveTrain drivetrain;
	public static ConveyorBelt conveyor_belt;
	
	public static Command autonomousCommand1;
	public static Command autonomousCommand2;
	public static Command autonomousCommand3;
	
	
	
	
	Thread visionThread;
	
//		maximum distance in inches we expect the robot to see
//		private static final double kMaxDistance = 24.0;
//		factor to convert sensor values to a distance in inches
//		private static final double kValueToInches = 0.125;
//
//		// proportional speed constant
//		private static final double kP = 7.0;
//
//		// integral speed constant
//		private static final double kI = 0.018;
//
//		// derivative speed constant
//		private static final double kD = 1.5;
//
//		private static final int kLeftMotorPort = 0;
//		private static final int kRightMotorPort = 1;
//		private static final int kUltrasonicPort = 0;

	Mat mat; //camera

	/**
	 * The motor speed is set from the joystick while the RobotDrive turning
	 * value is assigned from the error between the setpoint and the gyro angle.
	 */

	public static Command autonomousMode;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    public void robotInit() {
    	
		conveyor_belt = new ConveyorBelt();
		drivetrain = new DriveTrain();
		oi = new OI();
			
		//TODO: Replace with new autonomous
		autonomousMode = new AutonomousMode(); 
		autonomousCommand1 = new AutonomousConveyor(0.3, 2.0);
		//autonomousCommand2 = new DriveStraight(120, 0.5);
		//autonomousCommand3 = new DriveStraight(21, 0.3);
		
    }

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    //This function is called periodically during autonomous

    public void autonomousInit() {

        // schedule the autonomous command (example)
    	// if (the autonomousCommand does not return a null set (is not teleop), then run the autonomousCommand
    	 autonomousMode.start();	 
    }

    @Override //newer GRIP code
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
//      int raw = m_ultrasonic.getValue();
//		double distance = raw * (double)(5.0/4.0);
//		System.out.println(distance);
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	if (autonomousMode != null){
    		System.out.println("kill command has run");
    		autonomousMode.cancel();
    	}
    	Robot.drivetrain.stop();
    }

    // This function is called when the disabled button is hit. You can use it to reset subsystems before shutting down.

    public void disabledInit(){
    	
    }

    // This function is called periodically during operator control
    public void teleopPeriodic() {
       Scheduler.getInstance().run();
   
      Robot.drivetrain.arcadeDrive(oi.leftStick.getY(), oi.leftStick.getX());
      if (Math.abs(oi.beltStick.getY()) < 0.5) {
    	 Robot.conveyor_belt.setSpeed(0); 
      } else if (oi.beltStick.getY() > 0.5) {
    	  Robot.conveyor_belt.setSpeed(-0.75*oi.beltStick.getY());
      } else if (oi.beltStick.getY() < -0.5) {
    	  Robot.conveyor_belt.setSpeed(-0.75*oi.beltStick.getY());
      } 
      Timer.delay(0.05); //THIS IS IMPORTANT -> the robot NEEDS a wait period in between the time it receives information from the joystick, otherwise it gets overloaded with information and shuts down. We tried 0.01 seconds which was too little so keep 0.05
      //in teleopPeriodic, the arcadeDrive method is continuously called. When you comment out that method, teleop doesn't crash	
   
    }
    
//    private class MyPidOutput implements PIDOutput {
//		@Override
//		public void pidWrite(double output) {
//			AnalogInput ultrasonic = new AnalogInput(0);
//			int raw = ultrasonic.getValue();
//			double distance = raw * (double)(5.0/4.0);
//			System.out.println(distance);
//		}
//	}
    
}