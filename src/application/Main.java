package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MySimulatorModel model= new MySimulatorModel(); //model
			ViewModel vm = new ViewModel(model);  // View-Model //Contains the model so that it can send commands to it
			model.addObserver(vm);
			
			
    		FXMLLoader fxl=new FXMLLoader(); //Loading hierarchical objects from a document XML.
    		BorderPane root= fxl.load(getClass().getResource("/application/mainWindow.fxml").openStream());
    		
//    		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
		
//         	View view= new mainWindowController(vm);
			mainWindowController mwc=fxl.getController(); // View
			
			vm.addObserver(mwc); //
//	
			mwc.setViewModel(vm);

			Scene scene = new Scene(root,850,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
