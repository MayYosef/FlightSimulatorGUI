package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.security.cert.CertPathChecker;
import java.util.Collections;
import java.util.Comparator;

import com.sun.javafx.css.parser.DeriveColorConverter;
import com.sun.javafx.scene.BoundsAccessor;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

//A class that represents the view of the colorful map of elevations

public class ColorfulMapDisplayer extends Canvas{ 
 private int[][] mapData; //Does not define the map - this is a definition that I will receive from the outside
 private Double csvH,csvW,cellSizeKm; //Initial location + cell size per km
 private Integer maxH;
 
// private StringProperty planeImageFile; //If users add a tag of the map

private Point2D planeLoc; //Position of plane - by matrix
private Point2D pGoal,pGoalMatrix;
private Image planeImage, goalImage, littleCircle;
private String pathView; //Holding the commands that need to go to the shortest route
private Double w, h, cellW, cellH; //Pixels in the outer width
private Double planeLocX, planeLocY; //The position of the plane as sampled - not according to the matrix

public ColorfulMapDisplayer() {
	pathView=null;
   this.planeLoc=new Point2D(0,0);
// this.planeImageFile= new SimpleStringProperty();
 
 try {

	planeImage= new Image(new FileInputStream("./resources/picPl.png")); //It is possible instead of a path to use specificity
	goalImage= new Image(new FileInputStream("./resources/goal.png"));
	littleCircle= new Image(new FileInputStream("./resources/white_circle.png"));
 } catch (FileNotFoundException e) {
	e.printStackTrace();
 }
}

public void setMapData(int[][] mapData, String csvH, String csvW, String cellSizeKm, Integer max) { //Once someone feeds me the map I know I need to paint it
	this.mapData = mapData;
	this.csvH=Double.parseDouble(csvH);
	this.csvW=Double.parseDouble(csvW);
	this.cellSizeKm=Double.parseDouble(cellSizeKm);
	this.maxH=max;
	redraw();
}

public void setPathView(String pathView) { //Given a string that tells the way
	this.pathView =new String();
	this.pathView=pathView;
	redraw();
}


public void planeLocation(Double planeLocX, Double planeLocY) {
	//We will sample from the simulator and list the relevant location and send the drawings accordingly
this.planeLocX=planeLocX;
this.planeLocY= planeLocY;
// 
int i=0, j=0;
Double locX=Math.abs((planeLocX - csvH)/cellSizeKm);
i=(int) Math.round(locX);
System.out.println("locX staet:"+i);

Double locY=Math.abs((planeLocY - csvW)/cellSizeKm);
j=(int) Math.round(locY);
System.out.println("locY start:"+j);

Point2D p= new Point2D(i, j);
this.planeLoc=p;
System.out.println("draw map with new loc: " +planeLoc.getX()+","+planeLoc.getY());
this.pathView=null;
redraw();
}

public Point2D getpGoal() {
	return pGoal;
}

public Point2D getpGoalMatrix() {
	return pGoalMatrix;
}

public Point2D getPlaneLoc() {
	return planeLoc;
}

public int[][] getMapData() {
	return mapData;
}

public void setpGoal(Point2D pGoal) {
	this.pGoal= new Point2D(pGoal.getX(), pGoal.getY());
	double y= Math.round(pGoal.getX()/cellW);
	double x= Math.round(pGoal.getY()/cellH);
	this.pGoalMatrix = new Point2D(x ,y);	
	System.out.println("x = "+pGoalMatrix.getX() + "y= "+ pGoalMatrix.getY());
	redraw();
}

public void redraw()  { //Method of drawing - I want to activate it every time something changes - if you feed me a new map
	
	if(mapData!=null) {
		w=getWidth(); //Pixels in the outer width
		h=getHeight(); //Length
		
		cellW= w/mapData[0].length; //Width of each cell
		cellH= h/mapData.length; //Height of each cell
	
		System.out.println("cell w ="+cellW +" cell h"+ cellH);
		GraphicsContext gc= getGraphicsContext2D();
		
//		int min=0; //red
//		int middle=5; //yellow
//		int max=15; //green  
		
		gc.clearRect(0, 0, w, h);
		
		for(int i=0; i<mapData.length; i++) {     //Passes on the rows, the inner on the columns - that is, passes over each cell and paints according to the height
			for(int j=0; j<mapData[i].length; j++) {
								
				switch (mapData[i][j]) {
				case 0: 
					    gc.setFill(Color.RED.darker());
					    gc.fillRect(j*cellW, i*cellH, w, h);
					    gc.strokeText("0",j*(cellW), (i+1)*(cellH), 18);
				        break;
				case 1: 
					    gc.setFill(Color.RED.brighter());
					    gc.fillRect(j*cellW, i*cellH, w, h);
					    gc.strokeText("1",j*(cellW), (i+1)*(cellH), 18);
				        break;
				case 2: 
					    gc.setFill(Color.ORANGERED);
					    gc.fillRect(j*cellW, i*cellH, w, h);
					    gc.strokeText("2",j*(cellW), (i+1)*(cellH), 18);
					    break;
			
				case 3 :
					    gc.setFill(Color.ORANGE);
					    gc.fillRect(j*cellW, i*cellH, w, h);
					    gc.strokeText("3",j*(cellW), (i+1)*(cellH), 18);
				        break;
				case 4 :
					    gc.setFill(Color.YELLOW.brighter());
					    gc.fillRect(j*cellW, i*cellH, w, h);
					    gc.strokeText("4",j*(cellW), (i+1)*(cellH), 18);
					    break;
				case 5 :
				        gc.setFill(Color.YELLOW);
				        gc.fillRect(j*cellW, i*cellH, w, h);
				        gc.strokeText("5",j*(cellW), (i+1)*(cellH), 18);
				        break;
				case 6 :
				    gc.setFill(Color.GREENYELLOW.brighter());
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("6",j*(cellW), (i+1)*(cellH), 18);
				    break;
			
				case 7 :
				    gc.setFill(Color.GREENYELLOW.darker());
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("7",j*(cellW), (i+1)*(cellH), 18);
				    break;
			
				case 8 :
				    gc.setFill(Color.YELLOWGREEN.brighter());
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("8",j*(cellW), (i+1)*(cellH), 18);
				    break;
				case 9 :
				    gc.setFill(Color.YELLOWGREEN);
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("9",j*(cellW), (i+1)*(cellH), 18);
				    break;
				    
				case 10 :
				    gc.setFill(Color.LIMEGREEN.brighter());
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("10",j*(cellW), (i+1)*(cellH), 18);
				    break;
				case 11 :
				    gc.setFill(Color.LIMEGREEN.darker());
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("11",j*(cellW), (i+1)*(cellH), 18);
				    break;
				case 12:
				    gc.setFill(Color.GREEN.brighter());
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("12",j*(cellW), (i+1)*(cellH), 18);
				    break;
				case 13:
				    gc.setFill(Color.GREEN);
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("13",j*(cellW), (i+1)*(cellH), 18);
				    break;
				case 14 :
				    gc.setFill(Color.DARKGREEN);
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("14",j*(cellW), (i+1)*(cellH), 18);
				    break;
				case 15 :
				    gc.setFill(Color.DARKGREEN.darker());
				    gc.fillRect(j*cellW, i*cellH, w, h);
				    gc.strokeText("15",j*(cellW), (i+1)*(cellH), 18);
				    break;
				default:
					gc.setFill(Color.PINK);
					break;
			    ///Check Maximum Value - Need to paint by height areas !!  //		
		
			}
		}	
	}
		gc.drawImage(planeImage, planeLoc.getX()*cellW, planeLoc.getY()*cellH);
	  
		if(pGoal!=null) {
		gc.drawImage(goalImage, pGoal.getX(), pGoal.getY());
}
		
		if(pathView!=null) {
		String[] pathV= pathView.split(",");
		String toWhere; 
		
		Integer x= (int) planeLoc.getX();
	    Integer y= (int) planeLoc.getY();
	    System.out.println("planlocX: "+x + "planLocY: "+y );
		
		for(int len=0; len<pathV.length; len++) {
		  toWhere = pathV[len];
		  toFront();
	
		  System.out.println("to where = "+toWhere);
		  switch (toWhere){
			case "Right": 
				          if(x<mapData[0].length) {
				          x+=1;
    			          gc.drawImage(littleCircle, x*(cellW+2), y*(cellH+1));
				          }
					      break;
			case "Left": 
				          if(x>0) {
                          x-=1;
                          gc.drawImage(littleCircle, x*(cellW+2), y*(cellH+1));
				          }
                          break;
			case "Up":   
				         if(y>0) {
				         y-=1;
			             gc.drawImage(littleCircle, x*(cellW+2), y*(cellH+1));
				         }
				         break;
			case "Down": if(y<mapData.length) {
				         y+=1;
			             gc.drawImage(littleCircle, x*(cellW+2), y*(cellH+1));
			}
			             break;
			default: 
					 break;
				}
			}
		}
//		this.isExistPathView=false;
		}
}

//public String getPlaneImageFile() {
//	return planeImageFile.get();
//}
//
//public void setPlaneImageFile(String planeImageFile) {
//	this.planeImageFile.set(planeImageFile);
//}
}