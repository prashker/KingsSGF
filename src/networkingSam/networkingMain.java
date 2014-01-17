package networkingSam;

import java.io.IOException;

public class networkingMain {

	public static void main(String[] args) {
		
		
		try {
			ReactorSam server = new ReactorSam(10997);
			(new Thread(server)).start();
		}
		catch (IOException e) {
			System.out.println("Graceful catch");
		}
	}

}
