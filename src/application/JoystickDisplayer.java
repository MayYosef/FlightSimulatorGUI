package application;

import java.io.IOException;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class JoystickDisplayer{

    Circle circleOut ,circleIn;
    double centerX,centerY; //Mouse positioning
    double cInRadius;
    double initCenterX ,initCenterY; //On the radius-real-sampling location
	Double aileron, elevator;	
    
public JoystickDisplayer(Circle cOut, Circle cIn) {
	  this.circleOut= cOut;
	  this.circleIn=cIn;
	  this.centerX=0;
	  this.centerY=0;
	  this.cInRadius=0;
	  initCenterX= circleIn.getLayoutX();
	  initCenterY= circleIn.getLayoutY();
	  System.out.println("constractor: radius= "+cInRadius+" layout x,y: "+initCenterX+" , "+initCenterY);
}
	
private double dist(double x1, double y1, double x2, double y2) {
	return Math.sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2)); //Returns a positive root rounded upwards
}

public Double getAileron() {
		return aileron;
	}

	public Double getElevator() {
		return elevator;
	}

public void joystickOnMouseDrag(MouseEvent e){ // Responsible for what happens when moving the joystick
	
	double x2,y2;
	this.aileron=0.0;
    this.elevator=0.0;
	
	if(cInRadius == 0) { //initialization
		cInRadius = circleOut.getRadius(); 

		centerX = (circleIn.localToScene(circleIn.getBoundsInLocal()).getMinX() + circleIn.localToScene(circleIn.getBoundsInLocal()).getMaxX())/2;
		centerY = (circleIn.localToScene(circleIn.getBoundsInLocal()).getMinY() + circleIn.localToScene(circleIn.getBoundsInLocal()).getMaxY())/2;				
	}
	  System.out.println("2: radius= "+cInRadius+" layout x,y: "+initCenterX+" , "+initCenterY);
	
	double x1 = e.getSceneX(); //By the window size
	double y1 = e.getSceneY();
    x2=x1;
    y2=y1;
    System.out.println("x1 & x2= "+x2+ " y1 & y2= "+y2);
    
	double distance = dist(x1, y1, centerX, centerY); //The distance between the pressed point and the center of the inner circle
	if(distance <= cInRadius) {
		circleIn.setLayoutX(initCenterX + x1 - centerX);
		circleIn.setLayoutY(initCenterY + y1 - centerY);
	}
	
	else
	{
		if(x1 > centerX) {
		  double angle = Math.atan((y1-centerY)/(x1-centerX));
		  double a1 = cInRadius * Math.cos(angle); 
		  double a2 = cInRadius * Math.sin(angle);
			
			x2 = centerX + a1;
			y2 = centerY + a2;
			
			circleIn.setLayoutX(initCenterX + x2 - centerX);
			circleIn.setLayoutY(initCenterY + y2 - centerY);
		System.out.println("3: radius= "+cInRadius+" layout x,y: "+initCenterX+" , "+initCenterY+"center X+Y: "+centerX+","+centerY);
		}
		else
		{
			double angle = Math.atan((centerY - y1) / (centerX - x1));
			double a1 = cInRadius * Math.cos(angle);
			double a2 = cInRadius * Math.sin(angle);
			
			x2 = centerX - a1;
			y2 = centerY - a2;
			System.out.println("4: radius= "+cInRadius+" layout x,y: "+initCenterX+" , "+initCenterY+"center X+Y: "+centerX+","+centerY);
		}
		
		circleIn.setLayoutX(initCenterX + x2 - centerX);
		circleIn.setLayoutY(initCenterY + y2 - centerY);
		System.out.println("5: radius= "+cInRadius+" layout x,y: "+initCenterX+" , "+initCenterY+"center X+Y: "+centerX+","+centerY);
	}
	
//	 Setting the Aileron & Elevator values
	this.aileron=(x2 - centerX) / cInRadius;
	this.elevator=(centerY - y2) / cInRadius;
	
 System.out.println("airleron, elevator = "+aileron+","+elevator);	
}

public void joystickOnMouseRelease(MouseEvent e) { //Resets in mouse release mode
	System.out.println("Joystick released");
	
	this.circleIn.setLayoutX(initCenterX);
	this.circleIn.setLayoutY(initCenterY);
	
	System.out.println("centerX2: " +circleIn.getCenterX()+ "centerY2: "+circleIn.getCenterY() );
	System.out.println("l: " +circleIn.getLayoutX()+ " Ly: "+circleIn.getLayoutY());
	

	
}
}

