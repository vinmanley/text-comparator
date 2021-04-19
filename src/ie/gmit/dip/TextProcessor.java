package ie.gmit.dip;

import java.util.*;

/**
 * The class <b>TextProcessor</b> processes ArrayLists to Map objects through
 * threaded operations. <b>TextProcessor</b> implements the interface
 * <u>Runnable</u>.
 * 
 * @author Vincent Manley
 *
 */
public class TextProcessor implements Runnable {
	private List<String> preparedText;
	public Map<String, Integer> textMap;

	public TextProcessor(List<String> text) {
		this.preparedText = text;

	}

	/**
	 * Inherited from interface <u>Runnable</u>.
	 */
	@Override
	public void run() {
		textMap = getTermFrequencyMap(preparedText);
	}

	// Adapted from:
	// https://blog.nishtahir.com/fuzzy-string-matching-using-cosine-similarity/
	/**
	 * Interates over ArrayList and adds elements to Map object.
	 * 
	 * @param terms
	 * @return Map object
	 */
	public Map<String, Integer> getTermFrequencyMap(List<String> terms) {
		Map<String, Integer> termFrequencyMap = new HashMap<>();
		for (String term : terms) {
			Integer n = termFrequencyMap.get(term);
			n = (n == null) ? 1 : ++n;
			termFrequencyMap.put(term, n);
		}
		return termFrequencyMap;
	}
}
