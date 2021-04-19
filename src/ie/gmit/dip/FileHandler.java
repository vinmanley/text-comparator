package ie.gmit.dip;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The class <b>FileHandler</b> handles the files/URLs chosen in <b>Menu</b> and
 * converts the text to an ArrayList. These ArrayLists are then directed to the
 * class <b>Shinglizer</b> to be converted to shingles. <b>FileHandler</b>
 * implements the interface <u>Runnable</u>.
 * 
 * @author Vincent Manley
 *
 */
public class FileHandler implements Runnable {
	private InputStream input;
	private List<String> text = new ArrayList<String>();
	public List<String> shingles;
	Shinglizer s = new Shinglizer();
	public int shingleSize;

	/**
	 * Construtor for <b>FileHandler</b> class.
	 * 
	 * @param input
	 * @param shingleSize
	 */
	public FileHandler(InputStream input, int shingleSize) {
		this.input = input;
		this.shingleSize = shingleSize;
	}

	/**
	 * Inherited method from <u>Runnable</u> interface Calls the parse() method with
	 * input stream parameter.
	 */
	@Override
	public void run() {
		parse(input);
	}

	/**
	 * Converts text from input stream <i>input</i> to an ArrayList. Calls on
	 * <b>Shinglizer</b> to convert string contents to shingled text.
	 * 
	 * @param input
	 * @return shingles
	 */
	public List<String> parse(InputStream input) {
		input = this.input;
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String line;

		try {
			while ((line = br.readLine()) != null) {
				// Adapted from
				// https://stackoverflow.com/questions/18830813/how-can-i-remove-punctuation-from-input-text-in-java
				String[] split = line.replaceAll("[^\\p{L} ]", "").toLowerCase().split("\\s+");

				for (int i = 0; i < split.length; i++) {
					text.add(split[i].toLowerCase());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.shingles = s.shinglizer(text, shingleSize);

		return shingles;
	}
}