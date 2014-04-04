package gamePhasesSam;

import hexModelSam.HexModel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import counterModelSam.HeroThing;
import modelTestSam.GameEvent;
import modelTestSam.GameEventHandler;
import modelTestSam.GameModel;
import modelTestSam.Networkable;
import modelTestSam.PlayerModel;

public class RecruitHeroesPhase extends GamePhase {
	
	//Inner Class for Roll Metadata
	class RecruitRoll {
		private Integer roll = 0;
		private Integer numRolls = 0;
		private Integer requiredRoll = 0;
		private HexModel hexToMove;
		private HeroThing thingToMove;
		
		public RecruitRoll(HexModel h,  HeroThing t) {
			hexToMove = h;
			thingToMove = t;
			requiredRoll = t.value * 2;
		}
		
		public boolean canRecruit() {
			return roll >= requiredRoll;
		}
		
		public boolean canRoll() {
			return numRolls < 2;
		}
		
		public void addRoll(int i) {
			roll = roll + i;
			numRolls++;
		}
	}

	HashMap<PlayerModel, RecruitRoll> playersRecruit = new HashMap<PlayerModel, RecruitRoll>();
	
	int ended = 0;

	public RecruitHeroesPhase(GameModel m) {
		super(m);
		
		referenceToModel.chat.sysMessage("Recruit Heroes Phase");
		referenceToModel.chat.sysMessage("Drag a Hero from the Bank to a Hex you own, roll twice, if you get > 2*HeroValue, you successfully recruited it");
		referenceToModel.chat.sysMessage("Alternatively skip this turn and end your turn via button");
		
		referenceToModel.chat.sysMessage("Starting with: " + referenceToModel.gamePlayersManager.getPlayerByTurn().getName());
	}

	@Override
	protected void phaseHandler() {
		//FUTURE - PREBOOST, POSTBOOST
		
		//FROM
		//THING
		//HEX
		addPhaseHandler("SELECTRECRUIT", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				String hexToPlaceThing = (String) event.get("HEX");
				String thingToPlace = (String) event.get("THING");
				
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				HexModel gridFound = referenceToModel.grid.searchByID(hexToPlaceThing);
				HeroThing thingFound = referenceToModel.bank.searchByID(thingToPlace);

				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					//If player owns this hex
					if (gridFound.hasOwner(playerFound)) {
						//Should always be true (unless they're trying to recruit a non-hero due to other
						//bug that allows you to "recruit" from rack (future, FIX then remove workaround)
						if (thingFound != null && !playersRecruit.containsKey(playerFound)) {				
							playersRecruit.put(playerFound, new RecruitRoll(gridFound, thingFound));
							referenceToModel.chat.sysMessage(String.format("%s will try to recruit %s, needs to roll total %d", playerFound.getName(), thingFound.getName(), thingFound.value*2));
						} 
						else {
							//not sure what this will be...
							referenceToModel.chat.sysMessage(String.format("%s already selected a recruit (?), roll!", playerFound.getName()));
						}
					}
					else {
						referenceToModel.chat.sysMessage(String.format("%s tried recruiting %s to a HexTile they do not own", playerFound.getName(), thingFound.getName()));
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
			}
			
		});
		
		addPhaseHandler("ROLL", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				
				String player = (String) event.get("FROM");
				PlayerModel playerFound = referenceToModel.gamePlayersManager.getPlayer(player);
				int roll = (int) event.get("ROLL");
				
				//If it is this players turn
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {
					RecruitRoll r = playersRecruit.get(playerFound);
					//If have selected a monster
					if (r != null) {
						//should always be the case, because we cleanup after a roll
						if (r.canRoll()) {
							r.addRoll(roll);
							//successful roll
							if (r.canRecruit()) {
								//remove from bank
								referenceToModel.bank.removeFromBank(r.thingToMove);
								
								//add to hex
								r.hexToMove.addPlayerOwnedThingToHex(r.thingToMove, playerFound.getMyTurnOrder());
								referenceToModel.chat.sysMessage(String.format("%s has successfully recruited %s", playerFound.getName(), r.thingToMove.getName()));
								
								referenceToModel.gamePlayersManager.nextPlayerTurn();
								referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().getName() + "'s turn");
								ended++;
							}
							else {
								//if they can roll again
								if (r.canRoll()) {
									referenceToModel.chat.sysMessage(String.format("%s has rolled a total of %d out of %d required to recruit %s, roll again!", playerFound.getName(), r.roll, r.requiredRoll, r.thingToMove.getName()));
								}
								//if they have no more rolls
								else {
									referenceToModel.chat.sysMessage(String.format("%s has failed in recruiting %s", playerFound.getName(), r.thingToMove.getName()));
									//next player
									
									referenceToModel.gamePlayersManager.nextPlayerTurn();
									referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().getName() + "'s turn");
									ended++;
								}
							}
						}
						
					}
					else {
						referenceToModel.chat.sysMessage(String.format("%s has tried recruiting a Hero without selecting a hero first", playerFound.getName()));
					}
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
				
			}
			
		});
		
		//ENDTURN
		//FROM
		addPhaseHandler("ENDTURN", new GameEventHandler() {

			@Override
			public void handleEvent(Networkable network, SocketChannel socket, GameEvent event) {
				String player = (String) event.get("FROM");
								
				if (referenceToModel.gamePlayersManager.isThisPlayerTurn(player)) {	
					referenceToModel.gamePlayersManager.nextPlayerTurn();
					referenceToModel.chat.sysMessage(referenceToModel.gamePlayersManager.getPlayerByTurn().getName() + "'s turn");
					ended++;
				}
				
				if (isServer())
					network.sendAll(event.toJson());
				
				nextPhaseIfTime();
			}
			
		});
	}

	@Override
	public void nextPhaseIfTime() {
		if (ended == referenceToModel.gamePlayersManager.numPlayers()) {
			//NumRecruited + Ended == numPlayers
			removeHandlers();
			referenceToModel.state = new RecruitThingsPhase(referenceToModel);
		}
	}

}
