package uiSam;

import java.util.Observable;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import modelTestSam.Chat;
import modelTestSam.GameEvent;
import modelTestSam.JacksonSingleton;

public class ChatView extends AnchorPane implements KingsAndThingsView<Chat> {
	
	Chat chat;
	
	@FXML private TextArea chatArea;
	@FXML private TextField chatInput;
	@FXML private Button chatButton;
	
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("Got update");
		chatArea.appendText((String) arg1);		
	}

	@Override
	public void setBind(Chat m) {
		chat = m;
		chat.addObserver(this);
		System.out.println("chat bound");
		updateUI();
	}

	@Override
	public void updateUI() {
		//????
	}
	
	public void initialize() {
		chatArea.setEditable(false);
		chatArea.setStyle("-fx-focus-color: transparent;"); //to move to CSS file, disable glow when clicked

		chatArea.textProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				chatArea.setScrollTop(Double.MIN_VALUE);
			}
			
		});
		
		chatInput.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sendMessage();
			}
		});
		
		chatButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				sendMessage();
			}
			
		});

		
		System.out.println("Chat init");
	}
	
	public void sendMessage() {
		String possibleMsg = chatInput.getText();
		if (!possibleMsg.isEmpty()) {
			System.out.println("Sending: " + possibleMsg);
			GameEvent chatMsgEvent = new GameEvent("CHAT");
			chatMsgEvent.put("CONTENT", possibleMsg);
			
			BoardGameWindow.getInstance().networkMessageSend(chatMsgEvent);
			
			chatInput.clear();
		}
	}


}
