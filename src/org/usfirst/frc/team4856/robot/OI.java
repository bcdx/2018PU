package org.usfirst.frc.team4856.robot;
import edu.wpi.first.wpilibj.Relay;
//import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4856.robot.commands.*;
import org.usfirst.frc.team4856.robot.subsystems.Scaler;
import org.usfirst.frc.team4856.robot.subsystems.Scoop;
import org.usfirst.frc.team4856.robot.commands.ScaleUp;
import org.usfirst.frc.team4856.robot.commands.ScaleDown;


//import org.usfirst.frc.team4856.robot.commands.TankDriveWithJoysticks;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//declares variables of Joystick type (i.e. lefStick is an instance of Joystick.)
	//0, 1, and 2 refer to USB ports
	public Joystick leftStick = new Joystick(1); //LEFT, RIGHT --> facing the same direction as the robot
	public Joystick rightStick = new Joystick(0);
	public Joystick thirdStick = new Joystick(2); 

	public OI() {
		JoystickButton scoopReceive = new JoystickButton (thirdStick, 3);
		JoystickButton scoopPlace = new JoystickButton (thirdStick, 2);
		JoystickButton scaleUp = new JoystickButton (thirdStick, 5);
		JoystickButton scaleDown = new JoystickButton (thirdStick, 6);
		JoystickButton servoOpen = new JoystickButton (thirdStick, 9);
		JoystickButton servoClose = new JoystickButton (thirdStick, 8);
	
		scoopReceive.whileHeld(new PlaceGear()); 
		scoopPlace.whileHeld(new ReceiveGear());
		scaleUp.whileHeld(new ScaleUp());
		scaleDown.whileHeld(new ScaleDown());
		servoOpen.whenPressed(new OpenServo());
		servoClose.whenPressed(new CloseServo());
		
	}
	
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

