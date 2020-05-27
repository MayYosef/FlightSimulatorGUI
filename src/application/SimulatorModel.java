package application;

import java.io.File;

import javafx.geometry.Point2D;

public interface SimulatorModel {
 void connect(String ip, String port);
 void interpret(File f);
// int getRetuenVal();
 String getSolutionPath();
void findCalculatePath(String ip, int port, int[][] mapMatrix, Point2D planeLoc, Point2D goal);
void moveThrottle(Double d);
void moveRudder(Double d);
void moving(Double aileron, Double elevator);
void mapLoc();
Double getNewXlocP();
Double getNewYlocP();
}