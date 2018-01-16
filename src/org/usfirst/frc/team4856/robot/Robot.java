package org.usfirst.frc.team4856.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.vision.*;
import org.opencv.core.*;

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
	public static Scaler scaler;
	public static Scoop scoop;
	public static Bucket bucket;
	public static AnalogGyro gyro;
//	public static VisionMain vision;
	
	public static TalonSRX left1= new TalonSRX(3);
	public static TalonSRX left2= new TalonSRX(4);
	public static TalonSRX right1= new TalonSRX(1);
	public static TalonSRX right2= new TalonSRX(2);
	
	Thread visionThread;
	
	Joystick leftstick = new Joystick(1);
	Joystick rightstick = new Joystick(0);
	Joystick thirdstick = new Joystick(2);
	
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
        
        /*
    //START of newer GRIP code:
        // Get published values from GRIP using NetworkTables
        double angle = 0;
        for (double width : grip.getNumberArray("targets/width", new double[0])) {
        	double distance = -0.000002*width*width*width*width+0.000277*width*width*width-0.011785*width*width-0.019093*width+10.0866;
			//converting from pixels to meters
        	
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
    
}