package uiSam;

import modelTestSam.CombatZone;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class uiStart extends Application {
		
		public static void main(String[] args) {
			Application.launch();
		}

		@Override
		public void start(Stage stage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("ConnectDialog.fxml"));
			
			stage.setTitle("Kings and Things");
			stage.setScene(new Scene(root));
			stage.setResizable(false);
						
			stage.show();						
		}
		
}
