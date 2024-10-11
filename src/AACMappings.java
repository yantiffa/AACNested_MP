import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import main.java.edu.grinnell.csc207.util.AssociativeArray;
import main.java.edu.grinnell.csc207.util.KeyNotFoundException;
import main.java.edu.grinnell.csc207.util.NullKeyException;


/**
 * Creates a set of mappings of an AAC that has two levels, one for categories and then within each
 * category, it has images that have associated text to be spoken. This class provides the methods
 * for interacting with the categories and updating the set of images that would be shown and
 * handling an interactions.
 * 
 * This is written for fall24 section of CSC207
 * 
 * @author Catie Baker & Tiffany Yan
 *
 */
public class AACMappings implements AACPage {

	// fields
	AssociativeArray<String, AACCategory> categories;
	AACCategory current;
	Boolean top;


	/**
	 * Creates a set of mappings for the AAC based on the provided file. The file is read in to create
	 * categories and fill each of the categories with initial items. The file is formatted as the
	 * text location of the category followed by the text name of the category and then one line per
	 * item in the category that starts with > and then has the file name and text of that image
	 * 
	 * for instance: img/food/plate.png food >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 * 
	 * represents the file with two categories, food and clothing and food has french fries and
	 * watermelon and clothing has a collared shirt
	 * 
	 * @param filename the name of the file that stores the mapping information
	 */
	public AACMappings(String filename) {
		this.categories = new AssociativeArray<>();
		this.current = new AACCategory("");
		this.top = true;

		try {
			BufferedReader eyes = new BufferedReader(new FileReader(filename));
			String line;
			AACCategory category = null;

			while ((line = eyes.readLine()) != null) {

				if (!line.startsWith(">")) {
					String[] components = line.split(" ", 2);
					// Checks if the length of the line is less than two, if yes, it is not true
					if (components.length <= 1) {
						System.err.println("Wrong format of file");
						break;
					}
					// create a new category with the name
					category = new AACCategory(components[1]);
					try {
						categories.set(components[0], category);
					} catch (NullKeyException e) {
					}
				} else {
					String[] components = line.substring(1).split(" ", 2);
					// Checks if the length of the line is less than two, if yes, it is not true
					if (components.length <= 1) {
						System.err.println("Wrong format of file");
						break;
					}
					if (category != null) {
						category.addItem(components[0], components[1]);
					}
				}
			}
			eyes.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	/**
	 * Given the image location selected, it determines the action to be taken. This can be updating
	 * the information that should be displayed or returning text to be spoken. If the image provided
	 * is a category, it updates the AAC's current category to be the category associated with that
	 * image and returns the empty string. If the AAC is currently in a category and the image
	 * provided is in that category, it returns the text to be spoken.
	 * 
	 * @param imageLoc the location where the image is stored
	 * @return if there is text to be spoken, it returns that information, otherwise it returns the
	 *         empty string
	 * @throws NoSuchElementException if the image provided is not in the current category
	 */
	public String select(String imageLoc) throws NoSuchElementException {
		if (this.top) {
			try {
				AACCategory select = categories.get(imageLoc);
				current = select;
				// Move down, so now no longer at the top value
				this.top = false;
				return "";
			} catch (KeyNotFoundException e) {
			}
		} else {
			if (current.hasImage(imageLoc)) {
				return current.select(imageLoc);
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * Provides an array of all the images in the current category
	 * 
	 * @return the array of images in the current category; if there are no images, it should return
	 *         an empty array
	 */
	public String[] getImageLocs() {
		if (this.top) {
			return categories.keysAsStrings();
		}
		return current.getImageLocs();
	}

	/**
	 * Resets the current category of the AAC back to the default category
	 */
	public void reset() {
		this.current = new AACCategory("");
		this.top = true;
	}


	/**
	 * Writes the ACC mappings stored to a file. The file is formatted as the text location of the
	 * category followed by the text name of the category and then one line per item in the category
	 * that starts with > and then has the file name and text of that image
	 * 
	 * for instance: img/food/plate.png food >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 * 
	 * represents the file with two categories, food and clothing and food has french fries and
	 * watermelon and clothing has a collared shirt
	 * 
	 * @param filename the name of the file to write the AAC mapping to
	 */
	public void writeToFile(String filename) {
		try {
			FileWriter myWriter = new FileWriter(filename + ".txt");
			String[] categorylocs = categories.keysAsStrings();
			for (String categoryloc : categorylocs) {
				try {
					AACCategory category = categories.get(categoryloc);
					myWriter.write(categoryloc + " " + category.getCategory());
					for (String imageloc : category.getImageLocs()) {
						myWriter.write(">" + imageloc + " " + category.select(imageloc));
					}
				} catch (KeyNotFoundException e) {
				}
			}
			myWriter.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Adds the mapping to the current category (or the default category if that is the current
	 * category)
	 * 
	 * @param imageLoc the location of the image
	 * @param text the text associated with the image
	 */
	public void addItem(String imageLoc, String text) {
		if (this.top) {
			try {
				categories.set(imageLoc, new AACCategory(text));
			} catch (NullKeyException e){
			}
		}
		current.addItem(imageLoc, text);
	}


	/**
	 * Gets the name of the current category
	 * 
	 * @return returns the current category or the empty string if on the default category
	 */
	public String getCategory() {
		if (this.top) {
			return "";
		}
		return current.getCategory();
	}


	/**
	 * Determines if the provided image is in the set of images that can be displayed and false
	 * otherwise
	 * 
	 * @param imageLoc the location of the category
	 * @return true if it is in the set of images that can be displayed, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		if (this.top) {
			return categories.hasKey(imageLoc);
		}
		return current.hasImage(imageLoc);
	}
}
