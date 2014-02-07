package uiSam;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConnectDialog extends GridPane {
	@FXML private TextField hostField, portField;
	@FXML private Button hostButton, cancelButton, okButton;
	
	@FXML
	public void connectButtonClick() {
		
		String host = hostField.getText();
		int port = Integer.parseInt(portField.getText());
		
		System.out.printf("Connecting to: %s:%d\n", host, port);
		
		
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGameBoard.fxml"));
			root = (Parent) loader.load();	
			Stage stage = new Stage();
			stage.setTitle("----------------KINGS AND THINGS------------------");
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			
			Stage s = (Stage) okButton.getScene().getWindow();
			s.close();
			
			//BoardGameWindow boardGame = loader.getController();
			//boardGame.connect(host, port);

			final BoardGameWindow gameWindow = loader.getController();
			
			stage.show();
			
			gameWindow.connect(host, port);
			
			stage.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					System.out.println("Killing client");
					gameWindow.killNetwork();
				}
				
			});
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void cancelButtonClick() {
		System.out.println("Cancel");
		Stage s = (Stage) cancelButton.getScene().getWindow();
		s.close();
	}
	
	@FXML
	public void hostButtonClick() {
		
	}
	
}
