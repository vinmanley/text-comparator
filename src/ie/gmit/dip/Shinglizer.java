package ie.gmit.dip;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Shinglizer</b> converts strings contained in an ArrayList to shingled
 * text.
 * 
 * @author Vincent Manley
 *
 */
public class Shinglizer {
	public List<String> shingles = new ArrayList<String>();

	/**
	 * Constructor for <b>Shinglizer</b> class
	 */
	public Shinglizer() {

	}

	/**
	 * Converts strings contained in an ArrayList to shingled text. Accepts
	 * parameter <i>size</i> for choosing shingle size.
	 * 
	 * @param text
	 * @param size
	 * @return shingles
	 */
	public List<String> shinglizer(List<String> text, int size) {
		List<String> input = text;
		String shingle = "";
		StringBuilder sb = new StringBuilder();
		int i;

		switch (size) {
		case 1:
			shingles = text;
			break;
		case 2:
			for (i = 0; i < input.size() - 2; i += 3) {
				shingle = (input.get(i)) + " " + (input.get(i + 1)) + " " + (input.get(i + 2));
				shingles.add(shingle);
			}
			for (int j = i; j < input.size(); j++) {
				sb.append(input.get(j));
				sb.append(" ");
			}
			break;
		case 3:
			for (i = 0; i < input.size() - 4; i += 5) {
				shingle = (input.get(i)) + " " + (input.get(i + 1)) + " " + (input.get(i + 2)) + " "
						+ (input.get(i + 3)) + " " + (input.get(i + 4));
				shingles.add(shingle);
			}
			for (int j = i; j < input.size(); j++) {
				sb.append(input.get(j));
				sb.append(" ");
			}
			break;

		default:
			;
		}
		shingles.add(sb.toString());
		return shingles;
	}
}
