import java.util.NoSuchElementException;
import main.java.edu.grinnell.csc207.util.AssociativeArray;
import main.java.edu.grinnell.csc207.util.KeyNotFoundException;
import main.java.edu.grinnell.csc207.util.NullKeyException;


/**
 * Represents the mappings for a single category of items that should
 * be displayed
 * This is written for fall24 section of CSC207
 * @author Catie Baker & Tiffany Yan
 *
 */
public class AACCategory implements AACPage {
	//fields
	String name;
	AssociativeArray<String, String> items;
	
	/**
	 * Creates a new empty category with the given name
	 * @param name the name of the category
	 */
	public AACCategory(String name) {
		this.name = name;
		this.items = new AssociativeArray<>();
	}
	
	/**
	 * Adds the image location, text pairing to the category
	 * @param imageLoc the location of the image
	 * @param text the text that image should speak
	 */
	public void addItem(String imageLoc, String text) {
		try { 
			items.set(imageLoc,text);			
		} catch (NullKeyException e) {
			System.err.println("NullKeyException");
		}
	}

	/**
	 * Returns an array of all the images in the category
	 * @return the array of image locations; if there are no images,
	 * it should return an empty array
	 */
	public String[] getImageLocs() {
		return this.items.keysAsStrings();
	}

	/**
	 * Returns the name of the category
	 * @return the name of the category
	 */
	public String getCategory() {
		return this.name;
	}

	/**
	 * Returns the text associated with the given image in this category
	 * @param imageLoc the location of the image
	 * @return the text associated with the image
	 * @throws NoSuchElementException if the image provided is not in the current
	 * 		   category
	 */
	public String select(String imageLoc) throws NoSuchElementException {
		try{
			return this.items.get(imageLoc);
		} catch (KeyNotFoundException e) {
		}
		throw new NoSuchElementException();
	}

	/**
	 * Determines if the provided images is stored in the category
	 * @param imageLoc the location of the category
	 * @return true if it is in the category, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		return this.items.hasKey(imageLoc);
	}
}
