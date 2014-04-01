package uiSam;

import gamePhasesSam.MovementPhase;
import hexModelSam.HexModel;
import hexModelSam.HexModel.TileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import counterModelSam.Thing;
import counterModelSam.Thing.ThingAbility;
import modelTestSam.GameEvent;
import modelTestSam.GameModel;
import modelTestSam.PlayerModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MovementWindow extends VBox {
	
	@FXML HexTileView fromHexController;
	@FXML HexTileView toHexController;
	@FXML VBox moveGrid;
	
	public ArrayList<MoveThingView> thingsToMove = new ArrayList<MoveThingView>();

	
	GameModel referenceToModel;
	
	public void setupMovement(String fromHex, String toHex) {	
		referenceToModel = BoardGameWindow.getInstance().model;
		
		final HexModel from = referenceToModel.grid.searchByID(fromHex);
		final HexModel to = referenceToModel.grid.searchByID(toHex);
		
		fromHexController.setBind(from);
		toHexController.setBind(to);
		
		PlayerModel p = referenceToModel.gamePlayersManager.getPlayerByTurn();
		
		int costToMove = 1;
		
		if (Arrays.asList(TileType.SwampTile, TileType.MountainTile, TileType.ForestTile).contains(to.type)) {
			costToMove = 2;
		}

		for (Thing thing: from.stackByPlayer.get(p.getMyTurnOrder()).getStack().values()) {
			System.out.println("Trying to add thing: " + thing.getName() + " to " + p.getName());

			final MoveThingView m = new MoveThingView();
			m.errorArea.setEditable(false);
			//Add to an array
			thingsToMove.add(m);
			m.moveThing.setBind(thing);
			//Add to the VBox
			moveGrid.getChildren().add(m);
			
			
			//REALLY REALLY ___REALLY___ MAJOR HACK....UGH.
			int movesLeft = ((MovementPhase) referenceToModel.state).movesLeft.get(thing.getId()).get();
			
			m.numMovesLabel.setText("" + movesLeft);
			
			//not enough move points
			if (movesLeft < costToMove) {
				m.moveButton.setDisable(true);
				m.errorArea.appendText("Cannot move this Thing because of insufficient 'move credits'");
			}
			//if going to sea and cannot fly
			else if (to.type == TileType.SeaTile) {
				if (!thing.hasAbility(ThingAbility.FLYING)) {
					m.moveButton.setDisable(true);
					m.errorArea.appendText("Cannot move this Thing because you are trying to move to a SeaTile and this is not a flying creature");
				}
			}
			//leaving a tile with enemies, and cannot fly
			else if (from.howManyPlayersOnIt() > 1 && !thing.hasAbility(ThingAbility.FLYING)) {
				m.moveButton.setDisable(true);
				m.errorArea.appendText("Cannot move this Thing because you are trying to LEAVE an enemy-occupied tile and are not a flying creature");
			}
			else if (from.isUnexplored()) {
				m.moveButton.setDisable(true);
				m.errorArea.appendText("Cannot move this Thing because you are trying to LEAVE an unexplored hex");
			}
			else {
				m.moveButton.setDisable(false);
				m.errorArea.appendText("SAFE TO MOVE :) - COST: " + costToMove);
			}
				
			m.moveButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					m.moveButton.setDisable(true);
					m.errorArea.setText("MOVED!!!!!!!");
					
					GameEvent moveThing = new GameEvent("SINGLEMOVE");
					moveThing.put("THING", m.moveThing.thing.getId());
					moveThing.put("FROMHEX", from.getId());
					moveThing.put("TOHEX",  to.getId());
					BoardGameWindow.getInstance().networkMessageSend(moveThing);
				}
				
			});
			
		}

	}
	

}
