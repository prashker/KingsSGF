package hexModelSam;

public class HexGrid {

	// https://github.com/cmelchior/asciihexgrid/blob/master/src/main/java/dk/ilios/asciihexgrid/AsciiBoard.java
	// http://www.redblobgames.com/grids/hexagons/#map-storage
	
	// http://www.redblobgames.com/grids/hexagons/#neighbors
	// even Q
	
	private final int width;
	private final int height;
	private final int radius;
	
	
	private HexModel grid[][];
	
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
	}
	
	public HexGrid() {
		this(-3, 3, -3, 3); // 4 player layout
	}

	public void randomlySetAllQR() {
		//to modify when 2 player if we do 2 player
		for (int q = -3; q <=3; q++) {
			for (int r = -3; r <=3; r++) {
				setHexFromQR(q,r, new HexModel(HexModel.TileType.DESERT, this));
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
	}
	
	public HexModel getHexFromQR(int q, int r) {
		return grid[r + radius][q + radius + Math.min(0,  r)];
	}
	
	public static void main(String[] args) {
		HexGrid g = new HexGrid(-3, 3, -3, 3);		
		g.randomlySetAllQR();
		//g.printAll();
		//g.setHexFromQR(3, 1, new HexModel("Arf"));
		g.printAll();
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
		}
	}
	
	public void Demo1FixedGrid() {
		//Future, from XML
		/*
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -2, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -1, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -0, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		setHexFromQR(3, -3, new HexModel(HexModel.TileType.DESERT));
		*/
	}

}