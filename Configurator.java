// Scrola GUI Component
// Browser choice
// Template choice
// Fullscreen option

package scrola;

import java.io.*;
import java.util.*;
import java.lang.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Configurator extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		// Load the properties file
			Properties prop = new Properties();
			InputStream input = null;
			
			try {
				input = new FileInputStream("scrola/config.properties");
				prop.load(input);
				System.out.println(prop.getProperty("browsers"));
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
			finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		
		
		// Add and set elements on the GUI
			// The title
			primaryStage.setTitle("Scrola Configurator");
			// The grid alignment
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			// Browser choice label
			Label broChoiceLbl = new Label("Browser");
			grid.add(broChoiceLbl,0,0);
			// Browser choice combo box
			ObservableList<String> broOptions = FXCollections.observableArrayList("Google Chrome","Mozilla Firefox","Opera");
			final ComboBox broComboBox = new ComboBox(broOptions);
			grid.add(broComboBox,1,0);
			// Template choice label
			Label temChoiceLbl = new Label("Template");
			grid.add(temChoiceLbl,0,1);
			// Template choice combo box
			ObservableList<String> temOptions = FXCollections.observableArrayList("Default","Greenery");
			final ComboBox temComboBox = new ComboBox(temOptions);
			grid.add(temComboBox,1,1);
			// Fullscreen option label
			Label fcrLbl = new Label("Fullscreen");
			grid.add(fcrLbl,0,2);
			// Fullscreen option checkbox
			CheckBox fcrChkBox = new CheckBox();
			grid.add(fcrChkBox,1,2);
			// OK button
			Button okButton = new Button("OK");
			grid.add(okButton,2,3);
		
		// The GUI is ready to be shown
			Scene scene = new Scene(grid,300,200);
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
}