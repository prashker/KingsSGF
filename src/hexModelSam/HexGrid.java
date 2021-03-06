package hexModelSam;

import java.util.ArrayList;

import modelTestSam.KNTObject;
import modelTestSam.PlayerModel;

public class HexGrid extends KNTObject {

	// https://github.com/cmelchior/asciihexgrid/blob/master/src/main/java/dk/ilios/asciihexgrid/AsciiBoard.java
	// http://www.redblobgames.com/grids/hexagons/#map-storage
	
	// http://www.redblobgames.com/grids/hexagons/#neighbors
	// even Q
	
	private final int width;
	private final int height;
	private final int radius;
	
	
	//public ArrayList<String, HexModel> hexMapById; future, so hold on tight and hold me close, you're my hero, ponyo ponyo fishy in the sea
	public HexModel grid[][];
	
	//4 Player:
	//minQ: -3
	//maxQ: 3
	//minR: -3
	//maxR: 3
	
	//2 Player:
	//minQ: -2
	//maxQ: 2
	//minR: -2
	//maxR: 2

	public HexGrid(int minQ, int maxQ, int minR, int maxR) {
		this.width = maxQ - minQ + 1;
		this.height = maxR - minR + 1;
		this.radius = (maxQ + maxR)/2; //future????
		this.grid = new HexModel[width][height];
		setAllFaceDown();
	}
	
	public HexGrid() {
		this(-3, 3, -3, 3); // 4 player layout
	}

	public void randomlySetAllQR() {
		//to modify when 2 player if we do 2 player
		for (int q = -3; q <=3; q++) {
			for (int r = -3; r <=3; r++) {
				setHexFromQR(q,r, new HexModel(HexModel.TileType.DesertTile));
			}
		}
	}
	
	public void setAllFaceDown() {
		for (int q = -3; q <=3; q++) {
			for (int r = -3; r <=3; r++) {
				setHexFromQR(q,r, new HexModel(HexModel.TileType.FaceDownTile));
			}
		}
	}
	
	
	//From Axial Coordinates to Array Coordinates (Q,R)
	//See http://www.redblobgames.com/grids/hexagons/
	public void setHexFromQR(int q, int r, HexModel h) {
		int leftOffset = Math.min(0, r);
		int rightOffset = Math.max(0, r);
		
		int arrayRow = r + radius;
		int arrayCol = q + radius + leftOffset;
		
		//System.out.printf("(%d, %d) -> grid[%d][%d]\n", q, r, arrayRow, arrayCol);		

				
		if (arrayCol < 0 || arrayCol > 6 - rightOffset || arrayRow < 0 || arrayRow > 6)
			return;
		
		grid[arrayRow][arrayCol] = h;
		
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public HexModel getHexFromQR(int q, int r) {
		try {
			return grid[r + radius][q + radius + Math.min(0,  r)];
		}
		catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		HexGrid g = new HexGrid(-3, 3, -3, 3);		
		g.randomlySetAllQR();
		//g.printAll();
		//g.setHexFromQR(3, 1, new HexModel("Arf"));
	}
	
	public void printAll() {
		//DEBUG PRINT
		for (int y = 0; y <=6; y++ ) {
			for (int x = 0; x <= 6; x++) {
				if (grid[y][x] != null) {
					System.out.print("X");
				}
			}
			
			System.out.println();
			
			HexModel t = new HexModel(HexModel.TileType.JungleTile);
			
			System.out.println(t.type.toString());
		}
	}
	
	public HexModel searchByID(String id) {
		//n time but no way around this yet (until hashmap)
		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x] != null && grid[y][x].getId().equals(id))
					return grid[y][x];
			}
		}
		
		return null;
	}
	
	public void replaceHexGrid(HexModel[][] toReplace) {
		for (int y = 0; y < toReplace[0].length; y++) {
			for (int x = 0; x < toReplace[y].length; x++) {
				if (grid[y][x] != null) {
					grid[y][x] = toReplace[y][x];
					//grid[y][x].changeHex(toReplace[y][x].type, toReplace[y][x].getId());
				}
				else if (grid[y][x] == null && toReplace[y][x] != null) {
					//not sure when this case is needed, but might as well add
					grid[y][x] = toReplace[y][x];
				}
			}
		}
		this.setChanged();
		this.notifyObservers();
	}
	
	public ArrayList<HexModel> searchForAllOwnedByPlayer(PlayerModel p) {
		ArrayList<HexModel> results = new ArrayList<HexModel>();
		
		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x] != null) {
					if (grid[y][x].getOwner() != null && grid[y][x].getOwner().equals(p))
						results.add(grid[y][x]);
				}
			}
		}
		
		return results;
	}
	
	public ArrayList<HexModel> getHexesWithBattleConditions() {
		ArrayList<HexModel> results = new ArrayList<HexModel>();

		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x] != null) {
					int playersOnHex = grid[y][x].howManyPlayersOnIt();
					if ((grid[y][x].isUnexplored() && playersOnHex == 1) || (playersOnHex > 1))
						results.add(grid[y][x]);
				}
			}
		}
		
		return results;
	}
	
	public ArrayList<HexModel> getHexesWithThingsOnThem() {
		ArrayList<HexModel> results = new ArrayList<HexModel>();

		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x] != null) {
					int playersOnHex = grid[y][x].howManyPlayersOnIt();
					if (playersOnHex >= 1)
						results.add(grid[y][x]);
				}
			}
		}
		
		return results;
	}
	
	public ArrayList<HexModel> getNeighbors(String id) {
		ArrayList<HexModel> neighbors = new ArrayList<HexModel>();
		//n time but no way around this yet (until hashmap)
		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x] != null && grid[y][x].getId().equals(id)) {
					//Found it
					int r = y - radius;
					int q = x - radius - Math.min(0,  r);
					
					
					//PER AXIAL COORDINATES
					
					try { 
						HexModel h = getHexFromQR(q+1, r);
						if (h != null)
							neighbors.add(h);
					} catch (Exception e) {}

					try { 
						HexModel h = getHexFromQR(q+1, r-1);
						if (h != null)
							neighbors.add(h);
					} catch (Exception e) {}

					try { 
						HexModel h = getHexFromQR(q, r-1);
						if (h != null)
							neighbors.add(h);
					} catch (Exception e) {}
					
					try { 
						HexModel h = getHexFromQR(q-1, r);
						if (h != null)
							neighbors.add(h);
					} catch (Exception e) {}
					
					try { 
						HexModel h = getHexFromQR(q-1, r+1);
						if (h != null)
							neighbors.add(h);
					} catch (Exception e) {}
					
					try { 
						HexModel h = getHexFromQR(q, r+1);
						if (h != null)
							neighbors.add(h);
					} catch (Exception e) {}

					return neighbors;
				}
			}
		}
		
		return neighbors;
	}
	
	//replace hex
	//replace grid by instance vars
	

}
