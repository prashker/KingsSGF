package modelTestSam;

import java.util.Random;

public class Dice {
	
	public static Random rand = new Random();
	
	public static int Roll() {
		return rand.nextInt(6) + 1;
	}

}
