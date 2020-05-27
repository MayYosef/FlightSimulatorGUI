package application;

import javafx.scene.input.MouseEvent;

public interface View {
  void setViewModel(ViewModel vm); 
  void toConnect();
  void openFileCSV();
  void interpret();
  void joystickControl();
  void calculatePath();
  void loadCode();
}
