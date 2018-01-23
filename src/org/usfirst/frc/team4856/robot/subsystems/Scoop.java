package org.usfirst.frc.team4856.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


public class Scoop extends Subsystem {
	private SpeedController scoopMotor;
	public DigitalInput di_receive; //the limit switch that stops the scoop at the highest position
	public DigitalInput di_place; //the limit switch that stops the scoop at the lowest position
	
    //SpeedController armMotor = new Victor(1);
  
		
	public Scoop (int pwm_port, int dio_port_1, int dio_port_2) {
		super();                                                                                                                                                                                      
		scoopMotor = new Victor(pwm_port);
		di_receive = new DigitalInput(dio_port_1);
		di_place = new DigitalInput(dio_port_2);
	}
	
	 public void receive() {
		 if (!di_receive.get()){
		  scoopMotor.set(0.8);
		  System.out.println("receive gear method called");
		  System.out.println("switch" + di_receive.get());
		 }
		 else{
			 scoopMotor.set(0);
		 }
//		  when the switch is pressed, switch is true
	 }
	 
	 public void place() {
//		 if (!di_place.get()){
			 scoopMotor.set(-0.8);
//			 System.out.println("receive gear method called");
//			 System.out.println("switch" + di_place.get());
//		 }
//		 else {
//			 System.out.println(di_place.get());
//		 }
	 }
	 
	 public void stop() {
		 scoopMotor.set(0);
	 }
	 
	 // Put methods for controlling this subsystem here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}

