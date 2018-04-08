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
		//autonomousMode = new AutonomousMode(); 
    }

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    //This function is called periodically during autonomous

    public void autonomousInit() {

        // schedule the autonomous command (example)
    	// if (the autonomousCommand does not return a null set (is not teleop), then run the autonomousCommand
    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	//new DriveStraight(250, 0.5);
    	
    	CommandGroup forwardDump = new CommandGroup();
	    	forwardDump.addSequential(new DriveStraight(100, 0.3));
	    	forwardDump.addSequential(new DriveStraight(10, 0.2));
	    	forwardDump.addSequential(new AutonomousConveyor (0.6, 2.0));
	    	forwardDump.addSequential(new TurnRight(90,0.5));
	    	forwardDump.addSequential(new DriveStraight(70, 0.3));
	    	forwardDump.addSequential(new TurnLeft(90,0.5));
	    	forwardDump.addSequential(new DriveStraight(30,0.4));
    	
//    	CommandGroup leftLeft = new CommandGroup();
//	    	leftLeft.addSequential(new DriveStraight(120, 0.3));
//	    	leftLeft.addSequential(new TurnLeft(90, 0.3));
//	    	leftLeft.addSequential(new DriveStraight(21, 0.3));
//	    	
//	    	leftLeft.addSequential(new AutonomousConveyor(0.3, 2.0));
//	    	
	    CommandGroup impromptu =  new CommandGroup();
	    	impromptu.addSequential(new DriveStraight(40, 0.3));
	    	impromptu.addSequential(new TurnRight(30, 0.5));
	    	impromptu.addSequential(new DriveStraight(150, 0.3));
	    
////	    CommandGroup leftCenter = new CommandGroup();
////	    	leftLeft.addSequential(new DriveStraight(198 - RobotMap.robotLength, 0.5));
////	    	leftLeft.addSequential(new TurnLeft(90, 0.15));
////	    	leftLeft.addSequential(new DriveStraight(99, 0.5));
////	    	leftLeft.addSequential(new TurnRight(90, 0.15));
////	    	leftLeft.addSequential(new DriveStraight(22, 0.5));
////	    	leftLeft.addSequential(new TurnRight(90, 0.15));
////	    	leftLeft.addSequential(new DriveStraight(21, 0.3));
////	    	leftLeft.addSequential(new AutonomousConveyor(0.3, 2.0));
////	    
	    	
	    CommandGroup leftDump = new CommandGroup();
	    	leftDump.addSequential(new DriveStraight(40, 0.5));
	    	leftDump.addSequential(new TurnLeft(90, 0.3));
	    	leftDump.addSequential(new DriveStraight(165, 0.5));
	    	leftDump.addSequential(new TurnRight(90, 0.3));
	    	leftDump.addSequential(new DriveStraight(10, 0.4));
	    	leftDump.addSequential(new DriveStraight(30, 0.2));
	    	leftDump.addSequential(new AutonomousConveyor(0.3, 2.0));
	    	
	    	
//	    CommandGroup rightLeft = new CommandGroup();
//	    	rightLeft.addSequential(new DriveStraight(220, 0.5));
//	    	rightLeft.addSequential(new TurnLeft(90, 0.3));
//	    	rightLeft.addSequential(new DriveStraight(197, 0.5));
//	    	rightLeft.addSequential(new TurnLeft(90, 0.3));
//	    	rightLeft.addSequential(new DriveStraight(60.5, 0.5));
//	    	rightLeft.addSequential(new TurnLeft(90,0.3));
//	    	rightLeft.addSequential(new DriveStraight(21, 0.3));
//	    	rightLeft.addSequential(new AutonomousConveyor(0.3, 2.0));
//	   
	    CommandGroup leftRight = new CommandGroup();
	    	//leftRight.addSequential(new DriveStraight(220, 0.5));
//	    	leftRight.addSequential(new TurnRight(90, 0.3));
//	    	leftRight.addSequential(new DriveStraight(197, 0.5));
//	    	leftRight.addSequential(new TurnRight(90,0.3));
//	    	leftRight.addSequential(new DriveStraight(60.5, 0.5));
//	    	leftRight.addSequential(new TurnRight(90,0.3));
//	    	leftRight.addSequential(new DriveStraight(21, 0.3));
//	    	leftRight.addSequential(new AutonomousConveyor(0.3, 2.0));
	    	
////	    CommandGroup rightCenter = new CommandGroup();
////	    	rightCenter.addSequential(new DriveStraight(198 - RobotMap.robotLength, 0.5));
////	    	rightCenter.addSequential(new TurnRight(90, 0.15));
////	    	rightCenter.addSequential(new DriveStraight(99, 0.5));
////	    	rightCenter.addSequential(new TurnLeft(90, 0.15));
////	    	rightCenter.addSequential(new DriveStraight(22, 0.5));
////	    	rightCenter.addSequential(new TurnLeft(90, 0.15));
////	    	rightCenter.addSequential(new DriveStraight(21, 0.3));
////	    	rightCenter.addSequential(new AutonomousConveyor (0.3, 2.0));
////	
//	    	
	    CommandGroup rightRight = new CommandGroup();
	    	rightRight.addSequential(new DriveStraight(120, 0.5));
	    	rightRight.addSequential(new TurnLeft(90, 0.3));
	    	rightRight.addSequential(new DriveStraight(21, 0.3));
	    	rightRight.addSequential(new AutonomousConveyor(0.3, 2.0));
	    	
    	
	    System.out.println("Game Data: " + gameData);	
	    	
	    if (gameData.length() > 0) {
    		if (gameData.charAt(0) == 'L') {
    			//FROM LEFT
    			Timer.delay(2);
//    			Scheduler.getInstance().add();
//    			impromptu.start();
    			Scheduler.getInstance().add(leftDump);
    			leftDump.start();
    			
    			//Scheduler.getInstance().add(leftLeft);
    			//leftLeft.start();
    			//FROM CENTER
    			//Scheduler.getInstance().add(leftCenter);
    			//leftCenter.start();
    			//FROM RIGHT
    			//Scheduler.getInstance().add(rightLeft);
    			//rightLeft.start();
    			
    		} else if (gameData.charAt(0) == 'R') {
    			Timer.delay(2);
    			Scheduler.getInstance().add(forwardDump);
    			forwardDump.start();
    			//Scheduler.getInstance().add(impromptu);
    			//impromptu.start();
    			//FROM LEFT
    			//Scheduler.getInstance().add(leftRight);
    			//leftRight.start();
    			//FROM CENTER
    			//Scheduler.getInstance().add(rightCenter);
    			//rightCenter.start(); 
    			//FROM RIGHT
    			//Scheduler.getInstance().add(rightRight);
    			
    			//rightRight.start(); 
    		}
    	}
//    	
    	//autonomousMode.start();	
	    //impromptu.start();
	 
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