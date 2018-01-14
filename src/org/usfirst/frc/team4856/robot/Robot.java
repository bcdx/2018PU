package org.usfirst.frc.team4856.robot;

import java.io.IOException; //from newer GRIP code

//CANTalon support package
import com.ctre.CANTalon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import edu.wpi.cscore.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.vision.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4856.robot.commands.*;
import org.usfirst.frc.team4856.robot.subsystems.*;

import edu.wpi.first.wpilibj.vision.VisionPipeline;

import org.opencv.core.*;
import org.opencv.core.Core.*;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;
import org.opencv.objdetect.*;



/**
 * The VM (virtual machine) is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */a
public class Robot extends IterativeRobot {
	//NetworkTable table; // older GRIP code

	public NetworkTable table;
	
	public static OI oi;
	public static Scaler scaler;
	public static Scoop scoop;
	public static Bucket bucket;
	public static AnalogGyro gyro;
//	public static VisionMain vision;
	
	public static CANTalon left1= new CANTalon(3);
	public static CANTalon left2= new CANTalon(4);
	public static CANTalon right1= new CANTalon(1);
	public static CANTalon right2= new CANTalon(2);
	
	Thread visionThread;
	GripPipeline gp;
	
	Joystick leftstick = new Joystick(1);
	Joystick rightstick = new Joystick(0);
	Joystick thirdstick = new Joystick(2);
	
//	//Gyro code - Reference Sample Project
//	// gyro value of 360 is set to correspond to one full revolution
//	private static final double kAngleSetpoint = 0.0;
//	private static final double kP = 0.005; // proportional turning constant
//	private static final double kVoltsPerDegreePerSecond = 0.0128;  //gyro calibration constant, may need to be adjusted;
//	private static final int kLeftMotorPort = 0;
//	private static final int kRightMotorPort = 1;
//	private static final int kGyroPort = 0;
//	private static final int kJoystickPort = 0;

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
    	
//    	visionThread = new Thread(() -> {
//			// Get the Axis camera from CameraServer
//			AxisCamera camera = CameraServer.getInstance().addAxisCamera("axis-accc8e2708a3.local");
//			camera.setResolution(640, 480); // Set the resolution
//
//			// Get a CvSink. This will capture Mats from the camera
//			CvSink cvSink = CameraServer.getInstance().getVideo();
//			// Setup a CvSource. This will send images back to the Dashboard
//			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);
//
//			// Mats are very memory expensive. Lets reuse this Mat.
//			mat = new ArrayList<MatOfPoint>();
//			GripPipeline gp = new GripPipeline();
//			// This cannot be 'true'. The program will never exit if it is. This lets the robot stop this thread when 
//			//restarting robot code or deploying.		
//			
//			while (!Thread.interrupted()) {
//				// Tell the CvSink to grab a frame from the camera and put it
//				// in the source mat.  If there is an error notify the output.
//				if (cvSink.grabFrame(mat) == 0) { 
//					// Send the output the error.
//					outputStream.notifyError(cvSink.getError()); 
//					// skip the rest of the current iteration
//					continue;
//				}
//				// Put a rectangle on the image
//				Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
//						new Scalar(255, 255, 255), 5);
//				// Give the output stream a new image to display
//				outputStream.putFrame(mat);
//				gp.process(mat);
////				System.out.print("mat: " + mat);
//				mat = gp.filterContoursOutput();
//				
////				for(int x = 0; x <= mat.rows(); x++){
////					for (int y = 0; y <= mat.cols(); y++) {
////						System.out.println(mat.get(x, y));
////					}
////				}
//				
//			}
//		});  
    	
    	visionThread = new Thread(() -> {
			// Get the Axis camera from CameraServer
			AxisCamera camera = CameraServer.getInstance().addAxisCamera("axis-accc8e2708a3.local");
			// Set the resolution
			camera.setResolution(640, 480);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				// Put a rectangle on the image
				Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
						new Scalar(255, 255, 255), 5);
				// Give the output stream a new image to display
				outputStream.putFrame(mat);
				System.out.print("mat: " + mat);
				gp.process(mat);	
				ArrayList<MatOfPoint> output = new ArrayList<MatOfPoint>();
				
				output = gp.filterContoursOutput();
				
				Object[] outputArray = output.toArray();
				
//				Array[] allcontours;
				
			}
});
    	
    	
		visionThread.setDaemon(true);
		visionThread.start();
		
		scoop = new Scoop(0, 1, 2);		
		scaler = new Scaler();
		bucket = new Bucket();
		//gyro = new AnalogGyro(0);

		//gyro = new AnalogGyro(1);
		
		System.out.println("scaler inst in robot.java");
		System.out.println("scoop inst in robot.java");
		System.out.println("bucket inst in robot.java");
		//int angle = (int) gyro.getAngle(); 
		//System.out.println("Angle is " + angle);
				
		oi = new OI();
		autonomousWithoutGyro = new AutonomousWithoutGyro(); 
		left2.changeControlMode(CANTalon.TalonControlMode.Follower);
        right2.changeControlMode(CANTalon.TalonControlMode.Follower);
        // gyro.setSensitivity(kVoltsPerDegreePerSecond);

//		double[] defaultValue = new double[0];
//		while (true) {
//			
//		System.out.println("starting robotInit");
//			double[] widths = table.getNumberArray("width", defaultValue);
//			System.out.println("width table created" + widths.length);
//			for (double width : widths) {
//				System.out.println("width: "+ width);
//			}
////				double distance = -0.000002*width*width*width*width+0.000277*width*width*width-0.011785*width*width-0.019093*width+10.0866;
////				//converting from pixels to meters
////				angle = 0.052*distance*distance*distance*distance-1.03*distance*distance*distance+8.49*distance*distance-37.29*distance+93.64;
//////				//find angle from distance (using regression if v0 = 30)
////				System.out.println("width: "+ width);
////				System.out.println("distance: "+ distance);
////				System.out.println("angle: "+ angle);
////////			}
//////			
//////			Timer.delay(1);
////		} //END of older GRIP code
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
        double leftAxis = leftstick.getY();
        left1.set(-1*leftAxis);
        left2.changeControlMode(CANTalon.TalonControlMode.Follower);
        left2.set(left1.getDeviceID());
        double rightAxis = rightstick.getY();
        right1.set(1*rightAxis);
        right2.changeControlMode(CANTalon.TalonControlMode.Follower);
        right2.set(right1.getDeviceID());
        
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