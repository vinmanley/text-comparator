package ie.gmit.dip;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The class <b>Menu</b> provides the UI for the program and directs program
 * flow.
 * 
 * @author Vincent Manley
 * @Version 1.0
 *
 */
public class Menu {
	private Scanner s;
	private boolean keepRunning = true;
	private String fileOrURLChoice;
	private String fileLocation1;
	private String fileLocation2;
	private String continueOrReturnChoice;
	private InputStream subjectStream;
	private InputStream queryStream;
	private List<String> subjectPrepared;
	private List<String> queryPrepared;
	private Map<String, Integer> subjectMap;
	private Map<String, Integer> queryMap;
	private int percentageSimilarity;
	private int shingleSize;

	/**
	 * Constructor for <b>Menu</b> class.
	 */
	public Menu() {
		s = new Scanner(System.in);
	}

	/**
	 * Opens menu for display and provides user with ability to input.
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	public void go() throws IOException, URISyntaxException, InterruptedException {
		printMenu();

		while (keepRunning) {
			String input = s.next();
			char inputCheck = input.charAt(0);

			if (Character.isDigit(inputCheck)) {
				int choice = Integer.parseInt(input);
				switch (choice) {
				case 1:
					selectFileOrURL();
					break;
				case 2:
					cosineDistanceTextComparisonInfo();
					break;
				case 3:
					quit();
					break;

				default:
					System.out.println("Please enter a number between 1 and 3. Returning to Main Menu...");
					printMenu();
					;
				}
			} else {
				System.out.println("Please enter a number between 1 and 3. Returning to Main Menu...");
				printMenu();
			}
		}
	}

	/**
	 * Prints menu of options to screen.
	 */
	private void printMenu() {
		System.out.println("Text Comparison Program");
		System.out.println("Choose one of the options below:");
		System.out.println("1. Select Files/URLs for comparison");
		System.out.println("2. Learn more about Cosine Distance Text Comparison");
		System.out.println("3. Quit");
		System.out.println("");
		System.out.println("Type Option [1-3]>");
	}

	/**
	 * Allows user to choose between file or URL input.
	 * 
	 * @throws MalformedURLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void selectFileOrURL()
			throws MalformedURLException, FileNotFoundException, IOException, InterruptedException {

		System.out.println("Choose what text sources you would like to compare:");
		System.out.println("To choose two files located in a local folder type [F]");
		System.out.println("To choose two URLs type [U] >");

		fileOrURLChoice = s.next().toUpperCase();
		if (fileOrURLChoice.matches("F")) {
			System.out.println("");
			System.out.println("This application can compare .txt files.");
			System.out.println("Please input the filepath of your first file below.");
			System.out.println("Enter first filepath: ");
			fileLocation1 = s.next();
			System.out.println("Enter second filepath: ");
			fileLocation2 = s.next();
			System.out.println("");
			System.out.println("You have chosen to compare the folowing files:");
			System.out.println(fileLocation1);
			System.out.println("");
			System.out.println(fileLocation2);
			System.out.println("");
			System.out.println("If these files are correct type [C] to continue.");
			System.out.println("If you would like to choose a different option type [M] to return to Main Menu.");
			continueOrReturnChoice = s.next().toUpperCase();
			continueOrReturn();

		} else if (fileOrURLChoice.matches("U")) {
			System.out.println("This application can compare two URLs.");
			System.out.println("Enter first URL: ");
			fileLocation1 = s.next();
			System.out.println("Enter second URL: ");
			fileLocation2 = s.next();
			System.out.println("You have chosen to compare the folowing files:");
			System.out.println(fileLocation1);
			System.out.println(fileLocation2);
			System.out.println("");
			System.out.println("If these URLs are correct type [C] to continue.");
			System.out.println("If you would like to choose a different option type [M] to return to Main Menu>");
			continueOrReturnChoice = s.next().toUpperCase();
			continueOrReturn();
		} else {
			System.out.println("I'm sorry but I don't understand your reply. Please try again.");
			selectFileOrURL();
		}
	}

	/**
	 * Provides waypoint for user to return to Main Menu or continue with options
	 * chosen.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void continueOrReturn() throws IOException, InterruptedException {
		if (continueOrReturnChoice.matches("C")) {
			shingleSizeChoice();

		} else if (continueOrReturnChoice.matches("M")) {
			System.out.println("[INFO] Returning you to Main Menu...");
			printMenu();
		} else {
			System.out.println("I'm sorry I don't understand your reply. Please choose again...");
			System.out.println("Type [C] to continue or type [M] to return to Main Menu.");
			continueOrReturnChoice = s.next().toUpperCase();
			continueOrReturn();
		}
	}

	/**
	 * Allows user to choose between three shingle size options.
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void shingleSizeChoice() throws MalformedURLException, IOException, InterruptedException {
		String continueOrRechoose;

		System.out.println("");
		System.out
				.println("You can choose one of the following options for the shingle size used to compare the texts.");
		System.out.println("Option 1: Single word shingles (matches a lot of common words)");
		System.out.println("Option 2: 3-word shingles (just right!)");
		System.out.println("Option 3: 5-word shingles (longer shingles can miss short common phrases)");
		System.out.println("Type [1] for Option 1, type [2] for Option 2 or type [3] for Option 3>");
		shingleSize = Integer.parseInt(s.next());
		if (shingleSize != 1 && shingleSize != 2 && shingleSize != 3) {
			System.out.println("I'm sorry I don't understand your reply. Please choose again...");
			shingleSizeChoice();
		}
		System.out.println("You have chosen Option " + shingleSize + ". Do you want to continue?");
		System.out.println("Type [C] to continue.");
		System.out.println("Or type [S] to choose a different shingle size>");
		continueOrRechoose = s.next().toUpperCase();
		if (continueOrRechoose.matches("C")) {
			convertFileOrURLToStream(fileLocation1, fileLocation2);
		} else if (continueOrRechoose.matches("S")) {
			shingleSizeChoice();
		} else {
			System.out.println("I'm sorry I don't understand your reply. Please choose again...");
			shingleSizeChoice();
		}
	}

	/**
	 * Converts chosen file paths to input streams to be used in subsequent threaded
	 * operation. Assigns streams to responsible instance variables
	 * 
	 * @param subjectInput
	 * @param queryInput
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void convertFileOrURLToStream(String subjectInput, String queryInput)
			throws MalformedURLException, IOException, InterruptedException {
		if (fileOrURLChoice.matches("F")) {
			subjectStream = new FileInputStream(new File(subjectInput));
			queryStream = new FileInputStream(new File(queryInput));

		} else {
			subjectStream = new URL(subjectInput).openStream();
			queryStream = new URL(queryInput).openStream();
		}
		prepareText(subjectStream, queryStream, shingleSize);
	}

	/**
	 * Prepares subject and query texts by converting them to List Arrays through
	 * threaded operations to the <b>FileHandler</b> class.
	 * 
	 * @param subject
	 * @param query
	 * @param size
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void prepareText(InputStream subject, InputStream query, int size)
			throws IOException, InterruptedException {

		FileHandler fh1 = new FileHandler(subjectStream, size);
		Thread subjectThread = new Thread(fh1, "Subject Thread");
		FileHandler fh2 = new FileHandler(queryStream, size);
		Thread queryThread = new Thread(fh2, "Query Thread");

		subjectThread.start();
		queryThread.start();
		subjectThread.join();
		queryThread.join();

		this.subjectPrepared = fh1.shingles;
		this.queryPrepared = fh2.shingles;

		mapPreparedText();
	}

	/**
	 * Converts subject and query arrays to maps through threaded operations to the
	 * <b>TextProcessor</b> class. Directs Map objects to be compared.
	 * 
	 * @throws InterruptedException
	 */
	private void mapPreparedText() throws InterruptedException {
		TextProcessor tp1 = new TextProcessor(subjectPrepared);
		Thread subjectThread = new Thread(tp1, "Subject Thread");
		TextProcessor tp2 = new TextProcessor(queryPrepared);
		Thread queryThread = new Thread(tp2, "Query Thread");

		subjectThread.start();
		queryThread.start();
		subjectThread.join();
		queryThread.join();

		this.subjectMap = tp1.textMap;
		this.queryMap = tp2.textMap;

		compareTextMaps();

	}

	/**
	 * Compares texts and assigns percentage similarity result to instance variable
	 * <i>percentageSimilarity</i>. Directs user to results display.
	 */
	private void compareTextMaps() {
		double result;
		result = new TextComparator(subjectMap, queryMap).cosineSimilarity();
		percentageSimilarity = (int) (result * 100);

		postCompareDisplay();
	}

	/**
	 * Displays results of text comparison. Directs user to menu choice
	 */
	private void postCompareDisplay() {
		System.out.println("Comparison Complete!");
		System.out.println("The texts were " + percentageSimilarity + "% similar.");
		System.out.println("");
		System.out.println("");
		menuOrQuitChoice();
	}

	/**
	 * Guides user to viewing website with information about the method used for
	 * text comparison. Website opens in native browser. Directs user back to Main
	 * Menu.
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private void cosineDistanceTextComparisonInfo() throws IOException, URISyntaxException {
		System.out.println(
				"Would you like to visit a website to learn more about Cosine Distance Text Comparison? [Y/N] >");
		String websiteChoice = s.next().toUpperCase();

		try {
			if ((websiteChoice.matches("Y")) || (websiteChoice.matches("YES"))) {
				Desktop.getDesktop().browse(
						new URL("https://blog.nishtahir.com/fuzzy-string-matching-using-cosine-similarity/").toURI());
				// URL supplied above should open in user's default browser.
				System.out.println("[INFO] Returning you to Main Menu...");
				printMenu();
			} else if ((websiteChoice.matches("N")) || (websiteChoice.matches("NO"))) {
				System.out.println("[INFO] Returning you to Main Menu...");
				printMenu();
			} else {
				System.out.println("I'm sorry but I don't understand your reply. Please try again.");
				cosineDistanceTextComparisonInfo();
			}
		} catch (IOException | URISyntaxException e) {
			throw new IOException(
					"[ERROR] Unable to display website. Please check your connection and try again. " + e.getMessage());
		}
	}

	/**
	 * Allows user to decide between returning to Main Menu or quitting program.
	 * 
	 */
	private void menuOrQuitChoice() {
		System.out.println("Would you like to quit or return to Main Menu?");
		System.out.println("Type [M] to return to Main Menu or type [Q] to quit >");
		String menuOrQuitChoice = s.next().toUpperCase();

		if (menuOrQuitChoice.matches("M")) {
			System.out.println("");
			System.out.println("");
			System.out.println("");
			printMenu();
		} else if (menuOrQuitChoice.matches("Q")) {
			quit();
		} else {
			System.out.println("I'm sorry but I don't understand your reply. Please try again.");
			menuOrQuitChoice();
		}
	}

	/**
	 * Allows user to confirm quit choice or return to Main Menu.
	 * 
	 */
	private void quit() {
		System.out.println("Are you sure you want to quit? [Y/N] >");
		String quit = s.next().toUpperCase();

		if ((quit.matches("Y")) || (quit.matches("YES"))) {
			System.out.println("[Info] Shutting Down....");
			keepRunning = false;
		} else if ((quit.matches("N")) || (quit.matches("NO"))) {
			System.out.println("[INFO] Returning you to Main Menu...");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			printMenu();
		} else {
			System.out.println("I'm sorry but I don't understand your reply. Please try again.");
			quit();
		}
	}
}
