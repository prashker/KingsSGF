package uiSam;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import modelTestSam.Chat;
import modelTestSam.Dice;
import modelTestSam.GameEvent;

public class ChatView extends AnchorPane implements KingsAndThingsView<Chat> {
	
	Chat chat;
	
	@FXML private TextArea chatArea;
	@FXML private TextField chatInput;
	@FXML private Button chatButton;
	

	@Override
	public void setBind(final Chat m) {
		chat = m;

		chat.addObserver(new Observer() {
			public void update(Observable arg0, final Object arg1) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						chatArea.appendText((String) arg1);
					}
					
				});
				//chatArea.appendText((String) arg1);				
			}
		});
		
	}
	
	@Override
	public void updateBind(final Chat m) {
		/*
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				chatArea.appendText(m.lastMessage());
			}
			
		});
		*/
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
	}
	
	public void sendMessage() {
		String possibleMsg = chatInput.getText();
		if (!possibleMsg.isEmpty()) {
			
			//BYPASS TEMP
			if (possibleMsg.startsWith("/end")) {				
				GameEvent chatMsgEvent = new GameEvent("END");
				BoardGameWindow.getInstance().networkMessageSend(chatMsgEvent);
			}
			else if (possibleMsg.startsWith("/roll")) {
				GameEvent rollEvent = new GameEvent("ROLL");
				
				String manualRoll = possibleMsg.replace("/roll", "");
				if (!manualRoll.isEmpty()) {
					rollEvent.put("ROLL", Integer.parseInt(manualRoll));
				}
				else {
					rollEvent.put("ROLL", Dice.Roll());
				}
				BoardGameWindow.getInstance().networkMessageSend(rollEvent);
			}
			else if (possibleMsg.startsWith("/nick")) {
				GameEvent nickEvent = new GameEvent("NICK");
				nickEvent.put("NEWNICK", possibleMsg.replace("/nick",  "").trim());
				
				BoardGameWindow.getInstance().networkMessageSend(nickEvent);
			}
			//MASTER THIEF FUNCTIONALITY
			else if (possibleMsg.startsWith("/thief")) {
				GameEvent thiefEvent = new GameEvent("THIEF");
				thiefEvent.put("AGAINST", possibleMsg.replace("/thief",  "").trim());
				BoardGameWindow.getInstance().networkMessageSend(thiefEvent);
			}
			else if (possibleMsg.startsWith("/gold")) {
				GameEvent thiefEvent = new GameEvent("GOLD");				
				BoardGameWindow.getInstance().networkMessageSend(thiefEvent);
			}
			else if (possibleMsg.startsWith("/thing")) {
				GameEvent thiefEvent = new GameEvent("THING");				
				BoardGameWindow.getInstance().networkMessageSend(thiefEvent);
			}
			
			/*
			 future
			 else if (possibleMsg.startsWith("/")
			 universal command acceptor			 
			 */
			else {
				
				//System.out.println("Sending: " + possibleMsg);
				
				GameEvent chatMsgEvent = new GameEvent("CHAT");
				chatMsgEvent.put("CONTENT", possibleMsg);
				
				BoardGameWindow.getInstance().networkMessageSend(chatMsgEvent);
				
			}
			
			
			chatInput.clear();
		}
	}


}
