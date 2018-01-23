package org.usfirst.frc.team4856.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.vision.*;
import org.opencv.core.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
//import org.usfirst.frc.team4856.robot.Robot.MyPidOutput;
import org.usfirst.frc.team4856.robot.commands.*;
import org.usfirst.frc.team4856.robot.subsystems.*;

/**
 * The VM (virtual machine) is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static OI oi;
//	public static Scaler scaler;
//	public static Scoop scoop;
//	public static Bucket bucket;
//	public static AnalogGyro gyro;
//	public static VisionMain vision;
	

	
//	Ultrasonic ultra = new Ultrasonic(1,1); // creates the ultra object andassigns ultra to be an ultrasonic sensor which uses DigitalOutput 1 for 
    // the echo pulse and DigitalInput 1 for the trigger pulse
//	public void robotInit2() {
//		ultra.setAutomaticMode(true); // turns on automatic mode
//	}
//
//	public void ultrasonicSample() {
//		double range = ultra.getRangeInches(); // reads the range on the ultrasonic sensor 
//		System.out.println('')
//	}
	
	public static WPI_TalonSRX left1= new WPI_TalonSRX(3);
	public static WPI_TalonSRX left2= new WPI_TalonSRX(4);
	public static SpeedControllerGroup left = new SpeedControllerGroup(left1, left2);
	public static WPI_TalonSRX right1= new WPI_TalonSRX(1);
	public static WPI_TalonSRX right2= new WPI_TalonSRX(2);
	public static SpeedControllerGroup right = new SpeedControllerGroup(right1, right2);
	
	
//	DifferentialDrive drive = new DifferentialDrive(left, right);
	
	Thread visionThread;
	
	// maximum distance in inches we expect the robot to see

		private static final double kMaxDistance = 24.0;
		// factor to convert sensor values to a distance in inches
		private static final double kValueToInches = 0.125;

		// proportional speed constant
		private static final double kP = 7.0;

		// integral speed constant
		private static final double kI = 0.018;

		// derivative speed constant
		private static final double kD = 1.5;

		private static final int kLeftMotorPort = 0;
		private static final int kRightMotorPort = 1;
		private static final int kUltrasonicPort = 0;

//		private AnalogInput m_ultrasonic = new AnalogInput(kUltrasonicPort);
//		private PIDController m_pidController
//				= new PIDController(kP, kI, kD, m_ultrasonic, new MyPidOutput());

	
	Joystick leftstick = new Joystick(1);
	Joystick rightstick = new Joystick(0);
//	Joystick thirdstick = new Joystick(2);
	
//	private RobotDrive myRobot = new RobotDrive(kLeftMotorPort, kRightMotorPort);
//	private AnalogGyro gyro = new AnalogGyro(kGyroPort);
//	private Joystick joystick = new Joystick(kJoystickPort);

	Mat mat; //camera

	/**
	 * The motor speed is set from the joystick while the RobotDrive turning
	 * value is assigned from the error between the setpoint and the gyro angle.
	 */

	public static Command autonomousWithoutGyro;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    public void robotInit() {
		
		oi = new OI();
		
		//TODO: Replace with new autonomous
		autonomousWithoutGyro = new AutonomousWithoutGyro(); 
      
}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    /**
     * This function is called periodically during autonomous
    */

    public void autonomousInit() {
        // schedule the autonomous command (example)
    	//if (the autonomousCommand does not return a null set (is not teleop), then run the autonomousCommand
    	 autonomousWithoutGyro.start();	 
    }

    @Override //newer GRIP code
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
//      int raw = m_ultrasonic.getValue();
//		double distance = raw * (double)(5.0/4.0);
//		System.out.println(distance);
        
        /*
    //START of newer GRIP code:
        // Get published values from GRIP using NetworkTables
        double angle = 0;
        for (double width : grip.getNumberArray("targets/width", new double[0])) {
        	double distance = -0.000002*width*width*width*width+0.000277*width*width*width-0.011785*width*width-0.019093*width+10.0866;
			//converting from pixels to mete
			 * +rs
        	
			angle = 0.052*distance*distance*distance*distance-1.03*distance*distance*distance+8.49*distance*distance-37.29*distance+93.64;
			//find angle from distance (using regression if v0 = 30)
			
			System.out.println("Got contour with width=" + width);
			System.out.print(angle);
			
		} //END of newer GRIP code
		*/
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	autonomousWithoutGyro.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
   
        left1.set(-1*leftstick.getY());
        left2.set(-1*leftstick.getY());
        right1.set(rightstick.getY());
        right2.set(rightstick.getY());
        
//        double forward = leftstick.getRawAxis(1); // logitech gampad left X, positive is forward
//    	double turn = leftstick.getRawAxis(2); //logitech gampad right X, positive means turn right
//    	drive.arcadeDrive(forward, turn);
        
        
//        double leftAxis = leftstick.getY();
//        left1.set(-1*leftAxis);
//        left2.changeControlMode(TalonSRX.TalonControlMode.Follower);
//        left2.set(left1.getDeviceID());
//        double rightAxis = rightstick.getY();
//        right1.set(1*rightAxis);
//        right2.changeControlMode(CANTalon.TalonControlMode.Follower);
//        right2.set(right1.getDeviceID());
        
//        //Gyro - keep robot straight
//        double turningValue = (kAngleSetpoint - gyro.getAngle()) * kP;
//		// Invert the direction of the turn if we are going backwards
//		turningValue = Math.copySign(turningValue, joystick.getY());
//		myRobot.drive(joystick.getY(), turningValue);
//		
////    Gyro - keep robot straight
////    double turningValue = (kAngleSetpoint - gyro.getAngle()) * kP;
//		Invert the direction of the turn if we are going backwards
//		turningValue = Math.copySign(turningValue, joystick.getY());
//		myRobot.drive(joystick.getY(), turningValue);
		
    }
    
    private class MyPidOutput implements PIDOutput {
		@Override
		public void pidWrite(double output) {
			AnalogInput ultrasonic = new AnalogInput(0);
			int raw = ultrasonic.getValue();
			double distance = raw * (double)(5.0/4.0);
			System.out.println(distance);
		}
		
	}
    
}