package ie.gmit.dip;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The class <b>Runner</b> calls on <b>Menu</b> class to begin program.
 * 
 * @author Vincent Manley
 *
 */
public class Runner {
	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		new Menu().go();
	}
}