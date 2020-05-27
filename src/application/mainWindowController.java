package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mainWindowController implements View, Observer, Initializable {

    ViewModel vm;
    TextField ip, port;
    TextField ipServer, portServer;
    JoystickDisplayer joyDisplayer;
   public DoubleProperty aileron, elevator;
   private String solPath;
   private boolean ShortPath;
   private DoubleProperty planeLocX, planeLocY; 
   
	@FXML
    Circle cIn;
	@FXML
	Circle cOut;
    @FXML
	ColorfulMapDisplayer mapDisplayer; //A variable whose ID is in the file and then I get a reference to the object that was created
	@FXML
	TextArea txt;
	@FXML
	Slider rudderSlider;
	@FXML
	Slider throttleSlider; 
		
	public mainWindowController() { //constractor
	ShortPath=false;
	ip=new TextField();
	port= new TextField();
	ipServer= new TextField();
	portServer= new TextField();
	aileron= new SimpleDoubleProperty();
	elevator= new SimpleDoubleProperty();
	planeLocX= new SimpleDoubleProperty();
	planeLocY= new SimpleDoubleProperty();
	}
	
	@Override
	public void setViewModel(ViewModel vm) { 
		this.vm=vm;
		vm.ip.bind(ip.textProperty());
		vm.port.bind(port.textProperty());	
		vm.txt.bind(txt.textProperty());
		
		vm.ipServer.bind(ipServer.textProperty());
		vm.portServer.bind(portServer.textProperty());
		
		vm.rudder_vm.bind(rudderSlider.valueProperty());
		vm.throttle_vm.bind(throttleSlider.valueProperty());
		vm.aileron_vm.bind(aileron);
		vm.elevator_vm.bind(elevator);
		
		this.planeLocX.bind(vm.newXlocP_vm);
		this.planeLocY.bind(vm.newYlocP_vm);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) { //initialize method
		joyDisplayer= new JoystickDisplayer(cOut, cIn); //?
		
		
		   mapDisplayer.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {      
	            	Point2D p=new Point2D(e.getX(), e.getY());
	            	
	            	System.out.println(e.getX()+" "+e.getY());
	            	mapDisplayer.setpGoal(p);
	            	if(ShortPath) {
	            		calculatePath();
	            	}
	            }
	        });	   
		   
		   throttleSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println("init th: " +throttleSlider.getValue());
				vm.moveThrottle();
			}
		});
		   
		   rudderSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					System.out.println("init ru: " +rudderSlider.getValue());
					vm.moveRudder();
				}
			});
		   
	}
	
	public void setSolPath(String solPath) {
		this.solPath=solPath;
     	mapDisplayer.setPathView(this.solPath);
	}

	
	@Override
	public void toConnect() {
		 Stage popupwindow=new Stage();
		 popupwindow.initModality(Modality.APPLICATION_MODAL); //Defines a modal window that blocks events from being moved to another application window
		 popupwindow.setWidth(400);
		 popupwindow.setTitle("Connection to FlightGear Simulator");
		 VBox layout= new VBox(10);
		 Label label1= new Label("please Enter FlightGear Simulator's IP:");
		 Label label2= new Label("please Enter FlightGear Simulator's Port:");
		 ip.setMaxWidth(300);
		 port.setMaxWidth(300);
		 Button button1= new Button("Submit");
		     
		 button1.setOnAction(e -> {
			 popupwindow.close();
		 });
		     
		 layout.getChildren().addAll(label1, ip, label2,port, button1);
		      
		 layout.setAlignment(Pos.CENTER);
		 Scene scene1= new Scene(layout, 300, 250);
		 popupwindow.setScene(scene1);    
		 popupwindow.showAndWait(); //or show??
	
   		   vm.connect();
	}
	
	@Override
	public void interpret() {
		if(txt.getLength()==0) { 	//Only if we pressed against Levad	
			loadCode();
		}
		vm.interpret();
	}

	@Override
	public void calculatePath() {	
		 if(!ShortPath) {
		 Stage popupwindow=new Stage();
		 popupwindow.initModality(Modality.APPLICATION_MODAL); //Defines a modal window that blocks events from being moved to another application window
		 popupwindow.setWidth(400);
		 popupwindow.setTitle("Connection to a server that solves search problems");
		 VBox layout= new VBox(10);
		 Label label1= new Label("The server's IP:");
		 Label label2= new Label("The server's Port:  ");
		 ipServer.setMaxWidth(300);
		 portServer.setMaxWidth(300);
		 Button button1= new Button("connect");
		 
		 button1.setOnAction(e -> {
			 popupwindow.close();
		 });
		     
		 layout.getChildren().addAll(label1, ipServer, label2, portServer, button1);
		      
		 layout.setAlignment(Pos.CENTER);
		 Scene scene1= new Scene(layout, 300, 250);
		 popupwindow.setScene(scene1);    
		 popupwindow.showAndWait(); //or show??
		 }
		 	
		 if(mapDisplayer.getMapData()!=null && mapDisplayer.getpGoal()!=null) {
			 ShortPath=true;
			 vm.calculatePath(mapDisplayer.getMapData(), mapDisplayer.getPlaneLoc(), mapDisplayer.getpGoalMatrix());
		 }	
		}
		
	
	@Override
	public void loadCode() {
		FileChooser fc=new FileChooser();
		fc.setTitle("Open TXT File"); //title
		fc.setInitialDirectory(new File("./resources")); //We defined him as the hypolitical folder once we opened a dialogue
		fc.setSelectedExtensionFilter(new ExtensionFilter("TXT Files", "*.txt")); //What kind of files to show
	    File selectedFile = fc.showOpenDialog(null); 
	  
	    if (selectedFile != null) { 
	      //Read the text from the file and view it
	    	try {
	    	BufferedReader reading = new BufferedReader(new FileReader(selectedFile));		
		    String line;
		    while((line = reading.readLine())!=null) {
		    	txt.appendText(line+"\n");
		    }
		    reading.close();
	    	}
	    	catch (Exception e) {
			}
	    }
	}
	
	
	@Override
	public void openFileCSV() {
		FileChooser fc=new FileChooser();
		fc.setTitle("Open CSV File"); //title
		fc.setInitialDirectory(new File("./resources")); //We defined him as the hypolitical folder once we opened a dialogue
                                                                               //What kind of files to show
		fc.setSelectedExtensionFilter(new ExtensionFilter("Text Files", "*.csv"));
		File selectedFile = fc.showOpenDialog(null); //
	  
	    if (selectedFile != null) { //We want to take the values from the file and send them to display - we need a matrix
	
			try {
				BufferedReader reading = new BufferedReader(new FileReader(selectedFile));		
			    String line;
			    int i=0,j=0; 
			    String csvH=null,csvW=null; //Cell location 0,0 - its latitude and longitude
			    String cellSizeKm=null; //Cell size in square kilometer
			    int index=0;
//			    Point2D startPoint;
			    List<String> strings=new ArrayList<>();
			    List<Integer> integers= new ArrayList<>();
         			    
			    line = reading.readLine();
			    String[] values=line.split(",");
	    		csvH=values[0];
	    		csvW=values[1];
			    
	    		 line = reading.readLine();
	    		 values=line.split(",");
	    		 cellSizeKm=values[0];
	    		 
			    
			    while((line = reading.readLine())!=null) {
			        values=line.split(",");
			    	i=values.length;
			    	for(String s: values) {
			    	 strings.add(s);
			       }
			    }
			    
			    int numOfRows= strings.size()/i;
			    int[][] data = new int[numOfRows][i];
			    int k=0;
			    for(i=0; i<data.length; i++) {     
					for(j=0; j<data[i].length; j++,k++) {
						data[i][j]=Integer.parseInt(strings.get(k));
					}
			    }
			    
			Optional<Integer> maxH=strings.stream().map(new Function<String, Integer>() { //min is 0

				@Override
				public Integer apply(String t) {
					return Integer.parseInt(t);
				}		
			}).max(new Comparator<Integer>() {

				@Override
				public int compare(Integer o1, Integer o2) {
					return o1-o2;
				}
			});
			System.out.println("max: "+maxH.get() );
			    
		
			    //בדיקת הדפסה בשבילי:
//			    for(i=0; i<data.length; i++) {     
//					for(j=0; j<data[i].length; j++) {
//						System.out.print(data[i][j]+",");
//					}
//					System.out.println();
//			    }
			    
				reading.close();
			    mapDisplayer.setMapData(data,csvH,csvW,cellSizeKm,maxH.get());
			    
			    vm.mapLoc();
		 }

			
			 catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			 catch (IOException e) {
					e.printStackTrace();
				}
	    }
	}


@Override
public void joystickControl() {
this.cIn.setOnMouseReleased(new EventHandler<MouseEvent>() {

	@Override
	public void handle(MouseEvent event) {
		System.out.println("cin: "+ cIn.getRadius());
		joyDisplayer.joystickOnMouseRelease(event);
		aileron.set(joyDisplayer.getAileron());
		elevator.set(joyDisplayer.getElevator());
		System.out.println("aileron& elevator controller: "+aileron.get()+","+elevator.get());
		vm.movementJoy();
	}
});

this.cIn.setOnMouseDragged(new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			joyDisplayer.joystickOnMouseDrag(event);
		}
	});
}

	@Override
	public void update(Observable o, Object arg) {
		if(o==vm) {
			if(arg.equals("SolPath")) {
			 setSolPath(vm.solPath.get());
			}
			else if(arg.equals("locP")) {
				System.out.println("change loc to map: "+this.planeLocX.get()+" , "+ this.planeLocY.get());
		    mapDisplayer.planeLocation(this.planeLocX.get(), this.planeLocY.get());
//		    calculatePath();
		}
			}
	
	}

}