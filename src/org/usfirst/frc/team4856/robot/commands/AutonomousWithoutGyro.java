package org.usfirst.frc.team4856.robot.commands;

import org.usfirst.frc.team4856.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AutonomousWithoutGyro extends CommandGroup {
//	
	//CANTalon left1= Robot.left1;
	//CANTalon left2= Robot.left2;
	//CANTalon right1= Robot.right1;
	//CANTalon right2= Robot.right2;
	
	/*
	CANTalon 1 and 2 are right (negative)
	CANTalon 3 and 4 are left
	 */

	Timer timer;
	Servo servo;
	AnalogGyro gyro = new AnalogGyro(1);

    public AutonomousWithoutGyro() {
    	timer = new Timer();
  
        // Use requires() here to declare subsystem dependencies; eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
        gyro.reset();
    	
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	new CloseServo(); //to keep the servo open during autonomous mode at all times
    	
    	//double angle =  gyro.getAngle();
    	System.out.println("gyro angle: " + gyro.getAngle());
    	// Timer.delay(1);
    	
    	Robot.left1.set(0.5);
    	Robot.left2.set(0.5);
   		Robot.right1.set(0.5);
   		Robot.right2.set(0.5);
   		Timer.delay(1);  //move forward (left and right position)
   		System.out.println("gyro angle: " + gyro.getAngle());
   		//right has to be multipled by -1
//
//   		/*
//   		left1.set(0.5);
//    	left2.set(0.5);
//    	right1.set(0.5);
//    	right2.set(0.5);
//    	Timer.delay(0.35);  //turn right (left position)
//    	System.out.println("gyro: " + angle);
//    	*/
//    	
//   		left1.set(-0.5);
//    	left2.set(-0.5);
//    	right1.set(-0.5);
//    	right2.set(-0.5);
//    	Timer.delay(0.35);  //turn left (right position)
//    	
//   		angle =  gyro.getAngle();
//    	System.out.println("gyro: " + angle);
//    	
//    	left1.set(0.3);
//    	left2.set(0.3);
//    	right1.set(-0.3);
//    	right2.set(-0.3);
//    	Timer.delay(2.55);   //move forward (left and right position)
//    	
//    	angle = gyro.getAngle();
//    	System.out.println("gyro: " + angle);
//    	
//    	/*
//   		left1.set(-0.3);
//    	left2.set(-0.3);
//    	right1.set(-0.3);
//    	right2.set(-0.3);
//    	Timer.delay(0.1);  //align robot (left position)
//    	System.out.println("gyro: " + angle);
//    	*/
//    	
//    	left1.set(0.3);
//    	left2.set(0.3);
//    	right1.set(0.3);
//    	right2.set(0.3);
//    	Timer.delay(0.1); //align robot (right position)
//    	
//    	angle = gyro.getAngle();
//    	System.out.println("gyro: " + angle);
//    	
//   		left1.set(0.3);
//   		left2.set(0.3);
//    	right1.set(-0.3);
//    	right2.set(-0.3);
//    	Timer.delay(0.2); //move forward (left and right position)
//    	
//    	/*
//    	// placing gear 
//    	while(timer.get() > 4.3 && timer.get() < 4.6) {
//    		left1.set(0); // stop robot
//    		left2.set(0);
//   			right1.set(0);
//   			right2.set(0);
//   		
//   			new OpenServo();  // open scoop
//    	}
//    	
//    	while(timer.get() > 4.6 && timer.get() < 4.7) {
//    		new OpenServo();  // open scoop
//    		
//    		left1.set(0.3); 
//    		left2.set(0.3);
//   			right1.set(-0.3);
//   			right2.set(-0.3);
//    	}
//    	
//    	while(timer.get() > 4.7 && timer.get() < 5.0) {
//    		new OpenServo();  // open scoop
//    		
//    		left1.set(-0.4); 
//    		left2.set(-0.4);
//   			right1.set(0.4);
//   			right2.set(0.4);
//    	}
//    	 */
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timer.get() > 15; //stops autonomous mode when timer is longer than 15 seconds
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	Robot.left1.set(0);
        Robot.right1.set(0);
        Robot.left2.set(0);
        Robot.right2.set(0);
    }

    // Called when another command which requires one or more of the same subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}