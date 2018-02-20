package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;
import org.usfirst.frc.team4856.robot.RobotMap;
import org.usfirst.frc.team4856.robot.subsystems.ConveyorBelt;
import org.usfirst.frc.team4856.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.CommandGroup;

import edu.wpi.first.wpilibj.Ultrasonic;

public class AutonomousMode extends CommandGroup {
	public ADXRS450_Gyro gyroSPI = new ADXRS450_Gyro();
	AutonomousConveyor conveyor;
	Timer timer;
	Timer con_timer;
	double gyro_adj = 0.0;
	double temp = 0.5;
	double k1 = 0.002;
	int epsilon = 2;
	AnalogInput ultra = new AnalogInput(1);
	int bits;
	
	public void printUltraValues(){
		for (int i = 0; i < 10; ++i){
			double volts = ultra.getVoltage();
			int averageRaw = ultra.getAverageValue();
			double averageVolts = ultra.getAverageVoltage();
			System.out.println("voltage:" + averageVolts);
			Timer.delay(2.0);
		}
	}
	public void resetEncoders() {
		Robot.drivetrain.left2.setSelectedSensorPosition(0, 0, 0);
		Robot.drivetrain.right2.setSelectedSensorPosition(0, 0, 0);
		System.out.println("encoders reset");
		System.out.println("left encoder position: " + getLeftEncoderDistance());
		System.out.println("right encoder position: " + getRightEncoderDistance());
	}
	
	public double getLeftEncoderDistance() {
		return DriveTrain.left2.getSelectedSensorPosition(0) * RobotMap.inchesPerPulse;
	}
	
	public double getRightEncoderDistance() {
		return Robot.drivetrain.right2.getSelectedSensorPosition(0) * RobotMap.inchesPerPulse;
	}
	
	public void stop(){
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right1.set(ControlMode.PercentOutput, 0);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, 0);
	}
	
	public void setLeftSpeed(double speed){
	   	Robot.drivetrain.left1.set(ControlMode.PercentOutput, speed);
	    Robot.drivetrain.left2.set(ControlMode.PercentOutput, speed);
	}
	
	public void setRightSpeed(double speed){
	   	Robot.drivetrain.right1.set(ControlMode.PercentOutput, -speed);
	    Robot.drivetrain.right2.set(ControlMode.PercentOutput, -speed);
	}
	
	public double getVolts(){
		double volts = ultra.getVoltage();
		int averageRaw = ultra.getAverageValue();
		double averageVolts = ultra.getAverageVoltage();
		//System.out.println("voltage:" + averageVolts);
		return averageVolts;
	}
	public void driveDistance_obstruction(double targetDistance, double speed){
		System.out.println("first ultra reading:" + ultra.getAverageVoltage());
		Timer.delay(2.0);
		System.out.println("second ultra reading:" + ultra.getAverageVoltage());
		boolean toggle = false;
		double currentDistance = getRightEncoderDistance();
		setLeftSpeed(speed);
		setRightSpeed(speed);
		while (getRightEncoderDistance() < currentDistance + targetDistance){
			Timer.delay(0.05);
			while(getVolts() < 2){
				stop();
				Timer.delay(0.5);
				toggle =true;
					
			}
			//however, in teleop, this causes the robot to not respond to the joysticks after it encounters an obstacle, so fix this??
			
			if (toggle == true){
				setLeftSpeed(speed);
				setRightSpeed(speed);
				toggle = false;
			}
			
			
			if (gyroSPI.getAngle()>1) { 
				gyro_adj+=-k1;
			}
			else if (gyroSPI.getAngle()<-1){
				gyro_adj+=k1;
			}
			else{
				gyro_adj=0;
			}
			
			setLeftSpeed(speed+gyro_adj);
		}
		stop();
		
	}
	public void driveDistance(double targetDistance, double speed){
//		resetEncoders();
////		while (getRightEncoderDistance() > 1){
////			System.out.println("reset");
////		}
		double currentDistance = getRightEncoderDistance();
//		System.out.println("current distance: " + getRightEncoderDistance());
	   	setLeftSpeed(speed);
	    setRightSpeed(speed);
		while (getRightEncoderDistance() < currentDistance + targetDistance){
			Timer.delay(0.05);
			if (gyroSPI.getAngle()>1) { 
				gyro_adj+=-k1;
			}
			else if (gyroSPI.getAngle()<-1){
				gyro_adj+=k1;
			}
			else{
				gyro_adj=0;
			}
			
			setLeftSpeed(speed+gyro_adj);
		}
		stop();
	}
	
	
	public void turnRight(double angle, double speed){
		double initialAngle = gyroSPI.getAngle();
		Timer.delay(0.01);
		double currentSpeed = speed;
		
		while (gyroSPI.getAngle() < initialAngle + angle) {
			Timer.delay(0.01);
			
			setLeftSpeed(currentSpeed);
			setRightSpeed(-currentSpeed);
			
			if(gyroSPI.getAngle() > initialAngle + angle - 20 && currentSpeed > 0.3) {
				currentSpeed = currentSpeed * 0.8;
			}
		}
		System.out.println("DONE TURNING RIGHT");
		stop();
	}
	
	public void turnLeft(double angle, double speed){
		double initialAngle = gyroSPI.getAngle();
		Timer.delay(0.01);
		double currentSpeed = speed;
		
		while (gyroSPI.getAngle() > initialAngle - angle) {
			Timer.delay(0.01);
			
			setLeftSpeed(-currentSpeed);
			setRightSpeed(currentSpeed);
			
			if(gyroSPI.getAngle() < initialAngle - angle + 20 && currentSpeed > 0.3) {
				currentSpeed = currentSpeed * 0.8;
			}
		}
		System.out.println(gyroSPI.getAngle());
		System.out.println("DONE TURNING LEFT");
		stop();
	}
	
	
	
	public void autonConveyor(double speed, double time) {
		con_timer.reset();
		con_timer.start();
		while (con_timer.get() < time) {
			Robot.conveyor_belt.setSpeed(speed);
//			System.out.println("conveying");
		}
		Robot.conveyor_belt.setSpeed(0);
		System.out.println("loop exited");
		Robot.conveyor_belt.stopBelt();
		System.out.println("stopped conveying");
	}

    public AutonomousMode() {
    	timer = new Timer();
    	con_timer = new Timer();
        // Use requires() here to declare subsystem dependencies; eg. requires(chassis);
    }
    
		

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	
//    	System.out.println("initializing");
//    	
//    	System.out.println(" s conveying");
//    	new AutonomousConveyor(0.5,5.0);
//    	System.out.println("e conveying");
//    	
//    	timer.reset();
//    	timer.start();
//    	gyroSPI.reset();
//    	resetEncoders();
//    	Timer.delay(0.05); //0.01 before
//    	System.out.println("initial encoder position: " + getRightEncoderDistance());
    	
//    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
//    	
//    	if (gameData.length() > 0) {
//    		if (gameData.charAt(0) == 'L') {
//    			//FROM LEFT
//    			driveDistance(120, 0.2);
//    			turnRight(90, 0.15);
//    			//FROM CENTER
//    			driveDistance(50, 0.2);
//    			turnLeft(90, 0.15);
//    			driveDistance(120, 0.2);
//    			turnRight(90, 0.15);
//    			driveDistance(80, 0.2);
//    			turnRight(90, 0.15);
//    			//FROM RIGHT
//    			driveDistance(120, 0.2);
//    			turnLeft(90, 0.15); 
//    		} else if (gameData.charAt(0) == 'R') {
//    			//FROM LEFT
//    			//FROM CENTER
//    			//FROM RIGHT
//    		}
// 	}
    	

    	driveDistance(120, 0.5);
		turnLeft(90, 0.15);
    	driveDistance(21, 0.3);
		autonConveyor(0.3, 2.0);
		
    	 
    	//printUltraValues();
    	//driveDistance_obstruction(600, 0.5);
    	//printUltraValues();
    	//driveDistance_obstruction(120, 0.5);
    	
    	//Robot.autonomousCommand2.start(); //drivestraight
    	//turn
    	//Robot.autonomousCommand3.start();//drivestraight
    	//Robot.autonomousCommand1.start(); //conveyor belt
    	
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
      Timer.delay(1.0);
      System.out.println(timer.get());
    	
	}
//copy drivestraightmethod; create method drivestraight_obstruction
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timer.get() > 10;
    }

    
    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("end");
    	stop();
    }

    // Called when another command which requires one or more of the same subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	System.out.println("interrupted");
    }
}