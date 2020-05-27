package application;

import java.io.File;
import java.io.FileWriter;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;

public class ViewModel extends Observable implements Observer {

	SimulatorModel model;
    public StringProperty ip ,port;
    public StringProperty ipServer ,portServer;
    public StringProperty txt, solPath;
	public DoubleProperty rudder_vm, throttle_vm;
	public DoubleProperty aileron_vm, elevator_vm;
	public DoubleProperty newXlocP_vm, newYlocP_vm;
	
	public ViewModel(SimulatorModel model) {
	  this.model=model;
	  ip=new SimpleStringProperty();
	  port=new SimpleStringProperty();
	  ipServer=new SimpleStringProperty();
	  portServer=new SimpleStringProperty();
	  txt=new SimpleStringProperty();
	rudder_vm= new SimpleDoubleProperty();
	throttle_vm=new SimpleDoubleProperty();
	solPath=new SimpleStringProperty();
	aileron_vm= new SimpleDoubleProperty();
	elevator_vm= new SimpleDoubleProperty();
	newXlocP_vm= new SimpleDoubleProperty();
	newYlocP_vm= new SimpleDoubleProperty();
	
	}
	
	
	public void connect() {
		 if(!(ip.get().equals("")) && !(port.get().equals(""))) {
		   model.connect(ip.get(), port.get());
	}
		 }
	
	public void interpret() {
		File f= new File("./resources/Interpret.txt"); //Opens a file
		try {
		FileWriter fw= new FileWriter(f);
		fw.write(txt.get());   //כותב לתוכו
		fw.flush(); 
		fw.close();
		}
		catch (Exception e) {
		}
		model.interpret(f);  //Activates the method in the model that it will actually do it
	}
	

	public void setSolPath(String solPath) {
		this.solPath.setValue(solPath);
	    setChanged();
	    notifyObservers("SolPath");
	}

	public void calculatePath(int[][] mapMatrix, Point2D planeLoc, Point2D goal) {
		 if(!(ipServer.get().equals("")) && !(portServer.get().equals(""))) {
		model.findCalculatePath(ipServer.get(), Integer.parseInt(portServer.get()), mapMatrix,planeLoc,goal);
	}
	}

	public void moveThrottle() {
		model.moveThrottle(throttle_vm.get());
		
	}


	public void moveRudder() {
      model.moveRudder(rudder_vm.get());		
	}


	public void movementJoy() {
		model.moving(aileron_vm.get(), elevator_vm.get());
	}


	public void mapLoc() {
		model.mapLoc();		
	}
	

	public void setNewXlocP_vm(Double newXlocP) {
		this.newXlocP_vm.set(newXlocP);
	}


	public void setNewYlocP_vm(Double newYlocP) {
		this.newYlocP_vm.set(newYlocP); 
		setChanged();
		notifyObservers("locP");
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o==model) {
			if(arg.equals("SolPath")) {
			 setSolPath(model.getSolutionPath());
			}
			else if(arg.equals("new_loc")) {
			setNewXlocP_vm(model.getNewXlocP());
			setNewYlocP_vm(model.getNewYlocP());
		}	
			}
	}
}